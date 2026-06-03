package iam.solucoesdigitais.baseappjwt.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UnidadeRequestDTO {
    
	
    @NotBlank(message = "O nome da unidade é obrigatório")
	@Size(min = 2, max = 100, message = "O nome da unidade deve ter entre 2 e 100 caracteres")
    private String nome;
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
