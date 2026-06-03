package iam.solucoesdigitais.baseappjwt.dto;


import iam.solucoesdigitais.enums.StatusChamado;
import iam.solucoesdigitais.enums.StatusMaterial;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StatusUpdateDTO {
    
    private StatusMaterial statusMaterial;
    
    private StatusChamado statusChamado;
    
    @Size(max = 500, message = "O parecer não pode ultrapassar 500 caracteres")
    private String parecerSuporte;
    
    // Getters e Setters
    public StatusMaterial getStatusMaterial() { return statusMaterial; }
    public void setStatusMaterial(StatusMaterial statusMaterial) { this.statusMaterial = statusMaterial; }
    
    public StatusChamado getStatusChamado() { return statusChamado; }
    public void setStatusChamado(StatusChamado statusChamado) { this.statusChamado = statusChamado; }
    
    public String getParecerSuporte() { return parecerSuporte; }
    public void setParecerSuporte(String parecerSuporte) { this.parecerSuporte = parecerSuporte; }
    
    @Override
    public String toString() {
        return "StatusUpdateDTO{" +
                "statusMaterial=" + statusMaterial +
                ", statusChamado=" + statusChamado +
                ", parecerSuporte='" + parecerSuporte + '\'' +
                '}';
    }
}
