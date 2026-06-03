package iam.solucoesdigitais.baseappjwt.dto;

import iam.solucoesdigitais.enums.TipoMaterial;
import iam.solucoesdigitais.enums.StatusMaterial;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

public class MaterialRequestDTO {
    
    @NotNull(message = "O tipo do material é obrigatório")
    private TipoMaterial tipo;
    
    @NotBlank(message = "O tombamento é obrigatório")
    @Size(min = 3, max = 50, message = "O tombamento deve ter entre 3 e 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9\\-]+$", message = "O tombamento deve conter apenas letras, números e hífens")
    private String tombamento;
    
    @NotNull(message = "O status do material é obrigatório")
    private StatusMaterial status;
    
    @NotNull(message = "O ID da unidade é obrigatório")
    private Long unidadeId;
    
    @NotNull(message = "O ID do setor é obrigatório")
    private Long setorId;
    
    @NotBlank(message = "A marca é obrigatória")
    @Size(min = 2, max = 50, message = "A marca deve ter entre 2 e 50 caracteres")
    private String marca;
    
    @Size(max = 500, message = "As especificações não podem ultrapassar 500 caracteres")
    private String especificacoes;
    
    @NotBlank(message = "O nome de quem cadastrou é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String cadastradoPor;
    
    // Getters e Setters
    public TipoMaterial getTipo() { return tipo; }
    public void setTipo(TipoMaterial tipo) { this.tipo = tipo; }
    
    public String getTombamento() { return tombamento; }
    public void setTombamento(String tombamento) { this.tombamento = tombamento; }
    
    public StatusMaterial getStatus() { return status; }
    public void setStatus(StatusMaterial status) { this.status = status; }
    
    public Long getUnidadeId() { return unidadeId; }
    public void setUnidadeId(Long unidadeId) { this.unidadeId = unidadeId; }
    
    public Long getSetorId() { return setorId; }
    public void setSetorId(Long setorId) { this.setorId = setorId; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public String getEspecificacoes() { return especificacoes; }
    public void setEspecificacoes(String especificacoes) { this.especificacoes = especificacoes; }
    
    public String getCadastradoPor() { return cadastradoPor; }
    public void setCadastradoPor(String cadastradoPor) { this.cadastradoPor = cadastradoPor; }
}