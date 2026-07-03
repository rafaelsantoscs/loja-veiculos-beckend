package iam.solucoesdigitais.baseappjwt.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iam.solucoesdigitais.baseappjwt.config.UploadConfig;
import iam.solucoesdigitais.baseappjwt.dto.FotoVeiculoResponseDTO;
import iam.solucoesdigitais.baseappjwt.model.FotoVeiculo;
import iam.solucoesdigitais.baseappjwt.service.StorageService;

@Component
public class FotoVeiculoMapper {

    @Autowired
    private StorageService storageService;

    public FotoVeiculoResponseDTO toDTO(FotoVeiculo foto) {

        FotoVeiculoResponseDTO dto = new FotoVeiculoResponseDTO();

        dto.setId(foto.getId());

        dto.setUrl(
                storageService.montarUrl(
                        foto.getVeiculo().getId(),
                        foto.getNomeArquivo()));

        dto.setOrdem(foto.getOrdem());

        dto.setPrincipal(foto.getPrincipal());

        return dto;
    }
}

