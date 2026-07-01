package iam.solucoesdigitais.baseappjwt.mapper;

import iam.solucoesdigitais.baseappjwt.dto.VeiculoRequestDTO;
import iam.solucoesdigitais.baseappjwt.dto.VeiculoResponseDTO;
import iam.solucoesdigitais.enums.StatusVeiculo;
import iam.solucoesdigitais.baseappjwt.model.Veiculo;

public class VeiculoMapper {

    public static Veiculo toEntity(VeiculoRequestDTO dto) {

        Veiculo veiculo = new Veiculo();

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

        veiculo.setStatus(StatusVeiculo.DISPONIVEL);

        return veiculo;
    }

    public static VeiculoResponseDTO toResponse(Veiculo veiculo) {

        VeiculoResponseDTO dto = new VeiculoResponseDTO();

        dto.setId(veiculo.getId());

        dto.setMarca(veiculo.getMarca());
        dto.setModelo(veiculo.getModelo());
        dto.setVersao(veiculo.getVersao());

        dto.setAnoFabricacao(veiculo.getAnoFabricacao());
        dto.setAnoModelo(veiculo.getAnoModelo());

        dto.setPlaca(veiculo.getPlaca());
        dto.setCor(veiculo.getCor());

        dto.setCombustivel(veiculo.getCombustivel());
        dto.setCambio(veiculo.getCambio());

        dto.setQuilometragem(veiculo.getQuilometragem());

        dto.setPrecoCompra(veiculo.getPrecoCompra());
        dto.setPrecoVenda(veiculo.getPrecoVenda());

        dto.setDescricao(veiculo.getDescricao());

        dto.setStatus(veiculo.getStatus());
        dto.setDestaque(veiculo.getDestaque());

        dto.setAceitaTroca(veiculo.getAceitaTroca());
        dto.setUnicoDono(veiculo.getUnicoDono());
        dto.setBlindado(veiculo.getBlindado());

        dto.setDataEntrada(veiculo.getDataEntrada());

        return dto;
    }
}
