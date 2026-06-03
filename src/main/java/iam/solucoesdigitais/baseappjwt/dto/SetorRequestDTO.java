package iam.solucoesdigitais.baseappjwt.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SetorRequestDTO {
    
	
	
	
	@NotBlank(message = "O nome do setor é obrigatório")
    @Size(min = 2, max = 100, message = "O nome do setor deve ter entre 2 e 100 caracteres")
    private String nome;
	
    @NotNull(message = "O ID da unidade é obrigatório")
    private Long unidadeId;
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Long getUnidadeId() { return unidadeId; }
    public void setUnidadeId(Long unidadeId) { this.unidadeId = unidadeId; }
}
