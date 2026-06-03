package iam.solucoesdigitais.baseappjwt.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ManutencaoRequestDTO {
    
    @NotNull(message = "O ID do material é obrigatório")
    private Long materialId;
    
    private Long chamadoId;
    
    @NotBlank(message = "A descrição do problema é obrigatória")
    @Size(min = 10, max = 500, message = "A descrição do problema deve ter entre 10 e 500 caracteres")
    private String descricaoProblema;
    
    @NotBlank(message = "O nome do técnico é obrigatório")
    @Size(min = 2, max = 100, message = "O nome do técnico deve ter entre 2 e 100 caracteres")
    private String tecnico;
    
    // Getters e Setters
    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }
    
    public Long getChamadoId() { return chamadoId; }
    public void setChamadoId(Long chamadoId) { this.chamadoId = chamadoId; }
    
    public String getDescricaoProblema() { return descricaoProblema; }
    public void setDescricaoProblema(String descricaoProblema) { this.descricaoProblema = descricaoProblema; }
    
    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }
}