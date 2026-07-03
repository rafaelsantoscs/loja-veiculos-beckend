package iam.solucoesdigitais.baseappjwt.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iam.solucoesdigitais.baseappjwt.model.Despesa;
import iam.solucoesdigitais.baseappjwt.model.Lead;
import iam.solucoesdigitais.baseappjwt.model.Venda;
import iam.solucoesdigitais.baseappjwt.model.Veiculo;
import iam.solucoesdigitais.baseappjwt.repository.DespesaRepository;
import iam.solucoesdigitais.baseappjwt.repository.LeadRepository;
import iam.solucoesdigitais.baseappjwt.repository.VendaRepository;
import iam.solucoesdigitais.baseappjwt.repository.VeiculoRepository;
import iam.solucoesdigitais.enums.StatusLead;
import iam.solucoesdigitais.enums.StatusVeiculo;

/**
 * Consolida os números do CRM: dashboard, gráficos, fluxo de caixa
 * e relatórios premium (histórico de interesse, tempo em estoque,
 * precificação inteligente e giro).
 */
@Service
@Transactional(readOnly = true)
public class RelatorioService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    /* ── Dashboard: cards de resumo ─────────────────────────────── */

    public Map<String, Object> resumo() {

        List<Veiculo> veiculos = veiculoRepository.findAll();
        List<Venda> vendas = vendaRepository.findAllByOrderByDataVendaDesc();
        List<Lead> leads = leadRepository.findAll();

        YearMonth mesAtual = YearMonth.now();
        LocalDate inicioMes = mesAtual.atDay(1);
        LocalDate fimMes = mesAtual.atEndOfMonth();

        long emEstoque = veiculos.stream()
                .filter(v -> v.getStatus() != StatusVeiculo.VENDIDO)
                .count();

        long vendidosTotal = vendas.size();

        List<Venda> vendasMes = vendas.stream()
                .filter(v -> !v.getDataVenda().isBefore(inicioMes) && !v.getDataVenda().isAfter(fimMes))
                .collect(Collectors.toList());

        BigDecimal faturamentoMes = vendasMes.stream()
                .map(Venda::getValorVenda)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal despesasMes = despesaRepository
                .findByDataBetween(inicioMes, fimMes)
                .stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long leadsTotal = leads.size();
        long leadsMes = leads.stream()
                .filter(l -> {
                    LocalDate d = l.getDataCriacao().toLocalDate();
                    return !d.isBefore(inicioMes) && !d.isAfter(fimMes);
                })
                .count();

        long leadsConvertidos = leads.stream()
                .filter(l -> l.getStatus() == StatusLead.VENDIDO)
                .count();

        double conversao = leadsTotal == 0
                ? 0
                : (leadsConvertidos * 100.0) / leadsTotal;

        Map<String, Object> resumo = new LinkedHashMap<>();
        resumo.put("carrosEstoque", emEstoque);
        resumo.put("carrosVendidos", vendidosTotal);
        resumo.put("vendasMes", vendasMes.size());
        resumo.put("faturamentoMes", faturamentoMes);
        resumo.put("despesasMes", despesasMes);
        resumo.put("saldoMes", faturamentoMes.subtract(despesasMes));
        resumo.put("leadsRecebidos", leadsTotal);
        resumo.put("leadsMes", leadsMes);
        resumo.put("conversaoVendas", Math.round(conversao * 10.0) / 10.0);
        resumo.put("valorEstoque", veiculos.stream()
                .filter(v -> v.getStatus() != StatusVeiculo.VENDIDO)
                .map(v -> v.getPrecoVenda() == null ? BigDecimal.ZERO : v.getPrecoVenda())
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return resumo;
    }

    /* ── Gráfico: vendas por mês ────────────────────────────────── */

    public List<Map<String, Object>> vendasPorMes(int meses) {

        YearMonth atual = YearMonth.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");

        Map<YearMonth, List<Venda>> porMes = vendaRepository
                .findAllByOrderByDataVendaDesc()
                .stream()
                .collect(Collectors.groupingBy(v -> YearMonth.from(v.getDataVenda())));

        List<Map<String, Object>> resultado = new ArrayList<>();

        for (int i = meses - 1; i >= 0; i--) {
            YearMonth ym = atual.minusMonths(i);
            List<Venda> vendas = porMes.getOrDefault(ym, java.util.Collections.emptyList());

            BigDecimal faturamento = vendas.stream()
                    .map(Venda::getValorVenda)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, Object> item = new LinkedHashMap<>();
            item.put("mes", ym.format(fmt));
            item.put("quantidade", vendas.size());
            item.put("faturamento", faturamento);
            resultado.add(item);
        }

        return resultado;
    }

    /* ── Gráfico: vendas por vendedor ───────────────────────────── */

    public List<Map<String, Object>> vendasPorVendedor() {

        List<Venda> vendas = vendaRepository.findAllByOrderByDataVendaDesc();

        Map<String, List<Venda>> porVendedor = vendas.stream()
                .collect(Collectors.groupingBy(v ->
                        v.getVendedor() == null ? "Sem vendedor" : v.getVendedor().getNome()));

        return porVendedor.entrySet().stream()
                .map(e -> {
                    BigDecimal faturamento = e.getValue().stream()
                            .map(Venda::getValorVenda)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal comissao = e.getValue().stream()
                            .map(v -> v.getValorComissao() == null ? BigDecimal.ZERO : v.getValorComissao())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("vendedor", e.getKey());
                    item.put("quantidade", e.getValue().size());
                    item.put("faturamento", faturamento);
                    item.put("comissao", comissao);
                    return item;
                })
                .sorted((a, b) -> Integer.compare(
                        (int) b.get("quantidade"), (int) a.get("quantidade")))
                .collect(Collectors.toList());
    }

    /* ── Gráfico: veículos mais visualizados ────────────────────── */

    public List<Map<String, Object>> maisVisualizados(int limite) {

        return veiculoRepository.findAll().stream()
                .sorted(Comparator.comparing(
                        (Veiculo v) -> v.getVisualizacoes() == null ? 0 : v.getVisualizacoes())
                        .reversed())
                .limit(limite)
                .map(v -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("veiculoId", v.getId());
                    item.put("descricao", v.getMarca() + " " + v.getModelo() + " " + v.getAnoModelo());
                    item.put("visualizacoes", v.getVisualizacoes() == null ? 0 : v.getVisualizacoes());
                    item.put("contatos", leadRepository.countByVeiculoId(v.getId()));
                    item.put("status", v.getStatus());
                    return item;
                })
                .collect(Collectors.toList());
    }

    /* ── Fluxo de caixa ─────────────────────────────────────────── */

    public Map<String, Object> fluxoCaixa(LocalDate inicio, LocalDate fim) {

        if (inicio == null) inicio = YearMonth.now().atDay(1);
        if (fim == null) fim = YearMonth.now().atEndOfMonth();

        List<Venda> vendas = vendaRepository.findByDataVendaBetween(inicio, fim);
        List<Despesa> despesas = despesaRepository.findByDataBetween(inicio, fim);

        BigDecimal totalEntradas = vendas.stream()
                .map(Venda::getValorVenda)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSaidas = despesas.stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Saldo por dia
        Map<LocalDate, BigDecimal[]> porDia = new TreeMap<>();
        for (Venda v : vendas) {
            porDia.computeIfAbsent(v.getDataVenda(),
                    d -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
            porDia.get(v.getDataVenda())[0] =
                    porDia.get(v.getDataVenda())[0].add(v.getValorVenda());
        }
        for (Despesa d : despesas) {
            porDia.computeIfAbsent(d.getData(),
                    x -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
            porDia.get(d.getData())[1] =
                    porDia.get(d.getData())[1].add(d.getValor());
        }

        List<Map<String, Object>> saldoDiario = porDia.entrySet().stream()
                .map(e -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("data", e.getKey().toString());
                    item.put("entradas", e.getValue()[0]);
                    item.put("saidas", e.getValue()[1]);
                    item.put("saldo", e.getValue()[0].subtract(e.getValue()[1]));
                    return item;
                })
                .collect(Collectors.toList());

        // Despesas por categoria
        Map<String, BigDecimal> porCategoria = new HashMap<>();
        for (Despesa d : despesas) {
            porCategoria.merge(d.getCategoria().name(), d.getValor(), BigDecimal::add);
        }

        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("inicio", inicio.toString());
        resultado.put("fim", fim.toString());
        resultado.put("totalEntradas", totalEntradas);
        resultado.put("totalSaidas", totalSaidas);
        resultado.put("saldo", totalEntradas.subtract(totalSaidas));
        resultado.put("saldoDiario", saldoDiario);
        resultado.put("despesasPorCategoria", porCategoria);

        return resultado;
    }

    /* ── Premium: estoque analítico (lucro, tempo parado, sugestão) ─ */

    public List<Map<String, Object>> estoqueAnalitico() {

        List<Veiculo> veiculos = veiculoRepository.findAll();

        return veiculos.stream()
                .map(v -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("veiculoId", v.getId());
                    item.put("descricao", v.getMarca() + " " + v.getModelo() + " " + v.getAnoModelo());
                    item.put("status", v.getStatus());
                    item.put("dataEntrada", v.getDataEntrada() == null ? null : v.getDataEntrada().toString());
                    item.put("precoCompra", v.getPrecoCompra());
                    item.put("precoVenda", v.getPrecoVenda());
                    item.put("visualizacoes", v.getVisualizacoes() == null ? 0 : v.getVisualizacoes());
                    item.put("contatos", leadRepository.countByVeiculoId(v.getId()));

                    BigDecimal despesas = despesaRepository.findByVeiculoId(v.getId())
                            .stream()
                            .map(Despesa::getValor)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    item.put("despesas", despesas);

                    BigDecimal compra = v.getPrecoCompra() == null ? BigDecimal.ZERO : v.getPrecoCompra();

                    if (v.getStatus() == StatusVeiculo.VENDIDO) {
                        Venda venda = vendaRepository
                                .findFirstByVeiculoIdOrderByDataVendaDesc(v.getId())
                                .orElse(null);
                        if (venda != null) {
                            item.put("valorVenda", venda.getValorVenda());
                            item.put("lucro", venda.getValorVenda().subtract(compra).subtract(despesas));
                            if (v.getDataEntrada() != null) {
                                item.put("diasEmEstoque",
                                        ChronoUnit.DAYS.between(v.getDataEntrada(), venda.getDataVenda()));
                            }
                        }
                    } else {
                        BigDecimal precoVenda = v.getPrecoVenda() == null ? BigDecimal.ZERO : v.getPrecoVenda();
                        item.put("lucroPrevisto", precoVenda.subtract(compra).subtract(despesas));

                        long dias = v.getDataEntrada() == null
                                ? 0
                                : ChronoUnit.DAYS.between(v.getDataEntrada(), LocalDate.now());
                        item.put("diasEmEstoque", dias);

                        // Precificação inteligente: sugestão por tempo parado
                        if (dias >= 90) {
                            item.put("sugestao", "Veículo há " + dias
                                    + " dias em estoque. Sugestão: reduzir o preço em 5%.");
                            item.put("precoSugerido", precoVenda
                                    .multiply(BigDecimal.valueOf(0.95))
                                    .setScale(2, RoundingMode.HALF_UP));
                        } else if (dias >= 60) {
                            item.put("sugestao", "Veículo há " + dias
                                    + " dias em estoque. Sugestão: reduzir o preço em 3%.");
                            item.put("precoSugerido", precoVenda
                                    .multiply(BigDecimal.valueOf(0.97))
                                    .setScale(2, RoundingMode.HALF_UP));
                        }
                    }

                    return item;
                })
                .collect(Collectors.toList());
    }

    /* ── Premium: relatório de giro ─────────────────────────────── */

    public Map<String, Object> relatorioGiro() {

        List<Venda> vendas = vendaRepository.findAllByOrderByDataVendaDesc();

        // Agrupa vendas por modelo (marca + modelo)
        Map<String, List<Venda>> porModelo = vendas.stream()
                .filter(v -> v.getVeiculo() != null)
                .collect(Collectors.groupingBy(v ->
                        v.getVeiculo().getMarca() + " " + v.getVeiculo().getModelo()));

        List<Map<String, Object>> giroModelos = porModelo.entrySet().stream()
                .map(e -> {
                    BigDecimal lucroTotal = e.getValue().stream()
                            .map(v -> {
                                BigDecimal compra = v.getVeiculo().getPrecoCompra() == null
                                        ? BigDecimal.ZERO : v.getVeiculo().getPrecoCompra();
                                BigDecimal despesas = despesaRepository
                                        .findByVeiculoId(v.getVeiculo().getId())
                                        .stream()
                                        .map(Despesa::getValor)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                                return v.getValorVenda().subtract(compra).subtract(despesas);
                            })
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("modelo", e.getKey());
                    item.put("quantidadeVendida", e.getValue().size());
                    item.put("lucroTotal", lucroTotal);
                    return item;
                })
                .collect(Collectors.toList());

        List<Map<String, Object>> maisVendidos = giroModelos.stream()
                .sorted((a, b) -> Integer.compare(
                        (int) b.get("quantidadeVendida"), (int) a.get("quantidadeVendida")))
                .limit(10)
                .collect(Collectors.toList());

        List<Map<String, Object>> maiorLucro = giroModelos.stream()
                .sorted((a, b) -> ((BigDecimal) b.get("lucroTotal"))
                        .compareTo((BigDecimal) a.get("lucroTotal")))
                .limit(10)
                .collect(Collectors.toList());

        List<Map<String, Object>> menorLucro = giroModelos.stream()
                .sorted(Comparator.comparing(m -> (BigDecimal) m.get("lucroTotal")))
                .limit(10)
                .collect(Collectors.toList());

        // Tempo médio em estoque dos vendidos
        List<Long> diasVendidos = vendas.stream()
                .filter(v -> v.getVeiculo() != null && v.getVeiculo().getDataEntrada() != null)
                .map(v -> ChronoUnit.DAYS.between(v.getVeiculo().getDataEntrada(), v.getDataVenda()))
                .collect(Collectors.toList());

        double tempoMedio = diasVendidos.isEmpty()
                ? 0
                : diasVendidos.stream().mapToLong(Long::longValue).average().orElse(0);

        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("maisVendidos", maisVendidos);
        resultado.put("maiorLucro", maiorLucro);
        resultado.put("menorLucro", menorLucro);
        resultado.put("tempoMedioEstoqueDias", Math.round(tempoMedio));

        return resultado;
    }
}
