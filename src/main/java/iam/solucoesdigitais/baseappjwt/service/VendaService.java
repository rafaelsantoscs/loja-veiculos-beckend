package iam.solucoesdigitais.baseappjwt.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iam.solucoesdigitais.baseappjwt.dto.VendaRequestDTO;
import iam.solucoesdigitais.baseappjwt.dto.VendaResponseDTO;
import iam.solucoesdigitais.baseappjwt.model.Despesa;
import iam.solucoesdigitais.baseappjwt.model.Venda;
import iam.solucoesdigitais.baseappjwt.model.Veiculo;
import iam.solucoesdigitais.baseappjwt.model.Vendedor;
import iam.solucoesdigitais.baseappjwt.repository.DespesaRepository;
import iam.solucoesdigitais.baseappjwt.repository.VendaRepository;
import iam.solucoesdigitais.baseappjwt.repository.VeiculoRepository;
import iam.solucoesdigitais.baseappjwt.repository.VendedorRepository;
import iam.solucoesdigitais.enums.CategoriaDespesa;
import iam.solucoesdigitais.enums.StatusVeiculo;

@Service
@Transactional
public class VendaService {

    @Autowired
    private VendaRepository repository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    /**
     * Registra a venda: marca o veículo como VENDIDO e calcula a
     * comissão automaticamente pelo percentual do vendedor.
     */
    @Transactional
    public VendaResponseDTO registrar(VendaRequestDTO dto) {

        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        if (veiculo.getStatus() == StatusVeiculo.VENDIDO) {
            throw new RuntimeException("Este veículo já foi vendido");
        }

        Venda venda = new Venda();
        venda.setVeiculo(veiculo);
        venda.setValorVenda(dto.getValorVenda());
        venda.setDataVenda(dto.getDataVenda() == null ? LocalDate.now() : dto.getDataVenda());
        venda.setNomeComprador(dto.getNomeComprador());
        venda.setObservacao(dto.getObservacao());

        if (dto.getVendedorId() != null) {
            Vendedor vendedor = vendedorRepository.findById(dto.getVendedorId())
                    .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
            venda.setVendedor(vendedor);

            BigDecimal percentual = vendedor.getPercentualComissao() == null
                    ? BigDecimal.ZERO
                    : vendedor.getPercentualComissao();

            BigDecimal comissao = dto.getValorVenda()
                    .multiply(percentual)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            venda.setValorComissao(comissao);
        }

        repository.save(venda);

        // Comissão vira despesa vinculada ao veículo (para o lucro real)
        if (venda.getValorComissao() != null
                && venda.getValorComissao().compareTo(BigDecimal.ZERO) > 0) {

            Despesa comissaoDespesa = new Despesa();
            comissaoDespesa.setCategoria(CategoriaDespesa.COMISSAO);
            comissaoDespesa.setDescricao("Comissão da venda - "
                    + (venda.getVendedor() != null ? venda.getVendedor().getNome() : ""));
            comissaoDespesa.setValor(venda.getValorComissao());
            comissaoDespesa.setData(venda.getDataVenda());
            comissaoDespesa.setVeiculo(veiculo);
            despesaRepository.save(comissaoDespesa);
        }

        veiculo.setStatus(StatusVeiculo.VENDIDO);
        veiculoRepository.save(veiculo);

        return toResponse(venda);
    }

    public List<VendaResponseDTO> listar() {

        return repository.findAllByOrderByDataVendaDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void excluir(Long id) {

        Venda venda = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        // Devolve o veículo ao estoque
        Veiculo veiculo = venda.getVeiculo();
        veiculo.setStatus(StatusVeiculo.DISPONIVEL);
        veiculoRepository.save(veiculo);

        repository.delete(venda);
    }

    private VendaResponseDTO toResponse(Venda venda) {

        VendaResponseDTO dto = new VendaResponseDTO();
        dto.setId(venda.getId());
        dto.setValorVenda(venda.getValorVenda());
        dto.setValorComissao(venda.getValorComissao());
        dto.setDataVenda(venda.getDataVenda());
        dto.setNomeComprador(venda.getNomeComprador());
        dto.setObservacao(venda.getObservacao());

        Veiculo v = venda.getVeiculo();
        if (v != null) {
            dto.setVeiculoId(v.getId());
            dto.setVeiculoDescricao(v.getMarca() + " " + v.getModelo() + " " + v.getAnoModelo());
            dto.setPrecoCompra(v.getPrecoCompra());

            BigDecimal despesas = despesaRepository.findByVeiculoId(v.getId())
                    .stream()
                    .map(Despesa::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setTotalDespesas(despesas);

            BigDecimal compra = v.getPrecoCompra() == null ? BigDecimal.ZERO : v.getPrecoCompra();
            dto.setLucro(venda.getValorVenda().subtract(compra).subtract(despesas));
        }

        if (venda.getVendedor() != null) {
            dto.setVendedorId(venda.getVendedor().getId());
            dto.setVendedorNome(venda.getVendedor().getNome());
        }

        return dto;
    }
}
