package iam.solucoesdigitais.baseappjwt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iam.solucoesdigitais.baseappjwt.dto.DespesaRequestDTO;
import iam.solucoesdigitais.baseappjwt.dto.DespesaResponseDTO;
import iam.solucoesdigitais.baseappjwt.model.Despesa;
import iam.solucoesdigitais.baseappjwt.model.Veiculo;
import iam.solucoesdigitais.baseappjwt.repository.DespesaRepository;
import iam.solucoesdigitais.baseappjwt.repository.VeiculoRepository;

@Service
@Transactional
public class DespesaService {

    @Autowired
    private DespesaRepository repository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    public DespesaResponseDTO cadastrar(DespesaRequestDTO dto) {

        Despesa despesa = new Despesa();
        aplicar(despesa, dto);

        repository.save(despesa);

        return toResponse(despesa);
    }

    public DespesaResponseDTO atualizar(Long id, DespesaRequestDTO dto) {

        Despesa despesa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        aplicar(despesa, dto);

        repository.save(despesa);

        return toResponse(despesa);
    }

    public List<DespesaResponseDTO> listar(Long veiculoId) {

        List<Despesa> despesas = veiculoId == null
                ? repository.findAllByOrderByDataDesc()
                : repository.findByVeiculoId(veiculoId);

        return despesas.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private void aplicar(Despesa despesa, DespesaRequestDTO dto) {

        despesa.setCategoria(dto.getCategoria());
        despesa.setDescricao(dto.getDescricao());
        despesa.setValor(dto.getValor());
        despesa.setData(dto.getData());

        if (dto.getVeiculoId() != null) {
            Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                    .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
            despesa.setVeiculo(veiculo);
        } else {
            despesa.setVeiculo(null);
        }
    }

    private DespesaResponseDTO toResponse(Despesa despesa) {

        DespesaResponseDTO dto = new DespesaResponseDTO();
        dto.setId(despesa.getId());
        dto.setCategoria(despesa.getCategoria());
        dto.setDescricao(despesa.getDescricao());
        dto.setValor(despesa.getValor());
        dto.setData(despesa.getData());

        if (despesa.getVeiculo() != null) {
            Veiculo v = despesa.getVeiculo();
            dto.setVeiculoId(v.getId());
            dto.setVeiculoDescricao(v.getMarca() + " " + v.getModelo() + " " + v.getAnoModelo());
        }

        return dto;
    }
}
