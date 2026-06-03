package iam.solucoesdigitais.baseappjwt.dto;

import java.time.LocalDateTime;

public class StatusHistoricoDTO {
    private String status;
    private LocalDateTime data;
    private String observacao;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
