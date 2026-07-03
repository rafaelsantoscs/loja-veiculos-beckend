package iam.solucoesdigitais.baseappjwt.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import iam.solucoesdigitais.baseappjwt.dto.FotoVeiculoResponseDTO;
import iam.solucoesdigitais.baseappjwt.mapper.FotoVeiculoMapper;
import iam.solucoesdigitais.baseappjwt.model.FotoVeiculo;
import iam.solucoesdigitais.baseappjwt.model.Veiculo;
import iam.solucoesdigitais.baseappjwt.repository.FotoVeiculoRepository;
import iam.solucoesdigitais.baseappjwt.repository.VeiculoRepository;

@Service
public class FotoVeiculoService {

    @Autowired
    private FotoVeiculoRepository fotoRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private FotoVeiculoMapper mapper;

    public FotoVeiculoResponseDTO upload(Long veiculoId, MultipartFile file) throws IOException {

        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        String nomeArquivo = storageService.salvarArquivo(veiculoId, file);

        Integer ordem = fotoRepository
                .findByVeiculoIdOrderByOrdem(veiculoId)
                .size() + 1;

        FotoVeiculo foto = new FotoVeiculo();

        foto.setVeiculo(veiculo);
        foto.setNomeArquivo(nomeArquivo);
        foto.setOrdem(ordem);
        foto.setPrincipal(ordem == 1);

        fotoRepository.save(foto);

        return mapper.toDTO(foto);
    }

    public List<FotoVeiculoResponseDTO> listar(Long veiculoId) {

        return fotoRepository.findByVeiculoIdOrderByOrdem(veiculoId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public void excluir(Long fotoId,Long veiculoId) throws IOException {

        FotoVeiculo foto = fotoRepository.findById(fotoId)
                .orElseThrow(() -> new RuntimeException("Foto não encontrada"));

        storageService.excluirArquivo(
                foto.getVeiculo().getId(),
                foto.getNomeArquivo());

        fotoRepository.delete(foto);

        reorganizarOrdem(foto.getVeiculo().getId());
    }

    public void definirPrincipal(Long fotoId, Long veiculoid) {

        FotoVeiculo foto = fotoRepository.findById(fotoId)
                .orElseThrow(() -> new RuntimeException("Foto não encontrada"));

        List<FotoVeiculo> fotos = fotoRepository
                .findByVeiculoIdOrderByOrdem(foto.getVeiculo().getId());

        for (FotoVeiculo f : fotos) {
            f.setPrincipal(f.getId().equals(fotoId));
        }

        fotoRepository.saveAll(fotos);
    }

    private void reorganizarOrdem(Long veiculoId) {

        List<FotoVeiculo> fotos = fotoRepository
                .findByVeiculoIdOrderByOrdem(veiculoId);

        int ordem = 1;

        for (FotoVeiculo foto : fotos) {
            foto.setOrdem(ordem++);
        }

        fotoRepository.saveAll(fotos);
    }
}