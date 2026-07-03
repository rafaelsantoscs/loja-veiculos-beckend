package iam.solucoesdigitais.baseappjwt.dto;

import iam.solucoesdigitais.enums.StatusLead;

public class LeadUpdateDTO {

    private StatusLead status;
    private Long vendedorId;

    public StatusLead getStatus() {
        return status;
    }

    public void setStatus(StatusLead status) {
        this.status = status;
    }

    public Long getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Long vendedorId) {
        this.vendedorId = vendedorId;
    }
}
