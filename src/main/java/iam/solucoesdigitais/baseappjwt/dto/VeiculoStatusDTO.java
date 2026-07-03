package iam.solucoesdigitais.baseappjwt.dto;

import iam.solucoesdigitais.enums.StatusVeiculo;

public class VeiculoStatusDTO {

    private StatusVeiculo status;

    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }
}