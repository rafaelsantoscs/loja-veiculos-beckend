package iam.solucoesdigitais.baseappjwt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iam.solucoesdigitais.baseappjwt.dto.VeiculoRequestDTO;
import iam.solucoesdigitais.baseappjwt.dto.VeiculoResponseDTO;
import iam.solucoesdigitais.baseappjwt.dto.VeiculoStatusDTO;
import iam.solucoesdigitais.baseappjwt.mapper.VeiculoMapper;
import iam.solucoesdigitais.baseappjwt.model.FotoVeiculo;
import iam.solucoesdigitais.baseappjwt.model.Veiculo;
import iam.solucoesdigitais.baseappjwt.repository.FotoVeiculoRepository;
import iam.solucoesdigitais.baseappjwt.repository.VeiculoRepository;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository repository;

    @Autowired
    private FotoVeiculoRepository fotoRepository;

    @Autowired
    private StorageService storageService;
    
    public VeiculoResponseDTO atualizarStatus(
            Long id,
            VeiculoStatusDTO dto) {

        Veiculo veiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        veiculo.setStatus(dto.getStatus());

        repository.save(veiculo);

        return VeiculoMapper.toResponse(veiculo);
    }
    
    public VeiculoResponseDTO atualizar(Long id, VeiculoRequestDTO dto) {

        Veiculo veiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        veiculo.setMarca(dto.getMarca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setVersao(dto.getVersao());

        veiculo.setAnoFabricacao(dto.getAnoFabricacao());
        veiculo.setAnoModelo(dto.getAnoModelo());

        veiculo.setPlaca(dto.getPlaca());
        veiculo.setCor(dto.getCor());

        veiculo.setCombustivel(dto.getCombustivel());
        veiculo.setCambio(dto.getCambio());

        veiculo.setQuilometragem(dto.getQuilometragem());

        veiculo.setPrecoCompra(dto.getPrecoCompra());
        veiculo.setPrecoVenda(dto.getPrecoVenda());

        veiculo.setDescricao(dto.getDescricao());

        veiculo.setDestaque(dto.getDestaque());
        veiculo.setAceitaTroca(dto.getAceitaTroca());
        veiculo.setUnicoDono(dto.getUnicoDono());
        veiculo.setBlindado(dto.getBlindado());

        if (dto.getOpcionais() != null) {
            veiculo.getOpcionais().clear();
            veiculo.getOpcionais().addAll(dto.getOpcionais());
        }

        repository.save(veiculo);

        return VeiculoMapper.toResponse(veiculo);
    }

    /**
     * Incrementa o contador de visualizações (chamado pela página pública).
     */
    public void registrarVisualizacao(Long id) {

        Veiculo veiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        Integer atual = veiculo.getVisualizacoes() == null ? 0 : veiculo.getVisualizacoes();
        veiculo.setVisualizacoes(atual + 1);

        repository.save(veiculo);
    }

    public VeiculoResponseDTO cadastrar(VeiculoRequestDTO dto) {

        Veiculo veiculo = VeiculoMapper.toEntity(dto);

        repository.save(veiculo);

        return VeiculoMapper.toResponse(veiculo);
    }

    public List<VeiculoResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(v -> {
                    VeiculoResponseDTO dto = VeiculoMapper.toResponse(v);
                    dto.setFotoPrincipal(resolverFotoPrincipal(v.getId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public VeiculoResponseDTO buscar(Long id) {

        Veiculo veiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        VeiculoResponseDTO dto = VeiculoMapper.toResponse(veiculo);
        dto.setFotoPrincipal(resolverFotoPrincipal(id));
        return dto;
    }

    private String resolverFotoPrincipal(Long veiculoId) {
        // Busca a foto marcada como principal; se não houver, pega a primeira por ordem
        FotoVeiculo foto = fotoRepository
                .findFirstByVeiculoIdAndPrincipalTrue(veiculoId)
                .orElseGet(() -> fotoRepository
                        .findFirstByVeiculoIdOrderByOrdem(veiculoId)
                        .orElse(null));

        if (foto == null) return null;

        return storageService.montarUrl(veiculoId, foto.getNomeArquivo());
    }

    public void excluir(Long id) {

        repository.deleteById(id);
    }
}