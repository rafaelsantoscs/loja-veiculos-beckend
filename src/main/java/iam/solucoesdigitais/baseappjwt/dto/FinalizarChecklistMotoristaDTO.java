package iam.solucoesdigitais.baseappjwt.dto;

import java.util.List;

public class FinalizarChecklistMotoristaDTO {
    private Long checklistId;
    private String recebidoPor;
    private String obsGestor;
    private String status;
    
    private List<ItemRecebidoMotoristaDTO> itensRecebidos;
    
    public FinalizarChecklistMotoristaDTO() {}
    
    public FinalizarChecklistMotoristaDTO(Long checklistId, String recebidoPor, String obsGestor, String status,
            List<ItemRecebidoMotoristaDTO> itensRecebidos) {
        this.checklistId = checklistId;
        this.recebidoPor = recebidoPor;
        this.obsGestor = obsGestor;
        this.status = status;
        this.itensRecebidos = itensRecebidos;
    }

    // Getters e Setters
    public Long getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Long checklistId) {
        this.checklistId = checklistId;
    }

    public String getRecebidoPor() {
        return recebidoPor;
    }

    public void setRecebidoPor(String recebidoPor) {
        this.recebidoPor = recebidoPor;
    }
    
    public String getObsGestor() {
        return obsGestor;
    }

    public void setObsGestor(String obsGestor) {
        this.obsGestor = obsGestor;
    }
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ItemRecebidoMotoristaDTO> getItensRecebidos() {
        return itensRecebidos;
    }

    public void setItensRecebidos(List<ItemRecebidoMotoristaDTO> itensRecebidos) {
        this.itensRecebidos = itensRecebidos;
    }
}