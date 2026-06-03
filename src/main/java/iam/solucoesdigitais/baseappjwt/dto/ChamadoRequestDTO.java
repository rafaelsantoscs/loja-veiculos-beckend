package iam.solucoesdigitais.baseappjwt.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChamadoRequestDTO {
    
    @NotBlank(message = "O nome do usuário que abriu o chamado é obrigatório")
    @Size(min = 2, max = 100, message = "O nome do usuário deve ter entre 2 e 100 caracteres")
    private String usuarioAbertura;
    
    @NotNull(message = "O ID da unidade é obrigatório")
    private Long unidadeId;
    
    @NotNull(message = "O ID do setor é obrigatório")
    private Long setorId;
    
    @NotNull(message = "O ID do material é obrigatório")
    private Long materialId;
    
    @NotBlank(message = "A descrição do problema é obrigatória")
    @Size(min = 10, max = 1000, message = "A descrição do problema deve ter entre 10 e 1000 caracteres")
    private String descricaoProblema;
    
    // Getters e Setters
    public String getUsuarioAbertura() { return usuarioAbertura; }
    public void setUsuarioAbertura(String usuarioAbertura) { this.usuarioAbertura = usuarioAbertura; }
    
    public Long getUnidadeId() { return unidadeId; }
    public void setUnidadeId(Long unidadeId) { this.unidadeId = unidadeId; }
    
    public Long getSetorId() { return setorId; }
    public void setSetorId(Long setorId) { this.setorId = setorId; }
    
    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }
    
    public String getDescricaoProblema() { return descricaoProblema; }
    public void setDescricaoProblema(String descricaoProblema) { this.descricaoProblema = descricaoProblema; }
}
