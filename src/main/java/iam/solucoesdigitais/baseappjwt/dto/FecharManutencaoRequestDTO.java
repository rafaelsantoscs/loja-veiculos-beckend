package iam.solucoesdigitais.baseappjwt.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class FecharManutencaoRequestDTO {
    
    @NotBlank(message = "A descrição da solução é obrigatória")
    @Size(min = 10, max = 500, message = "A descrição da solução deve ter entre 10 e 500 caracteres")
    private String descricaoSolucao;
    
    // Getters e Setters
    public String getDescricaoSolucao() { return descricaoSolucao; }
    public void setDescricaoSolucao(String descricaoSolucao) { this.descricaoSolucao = descricaoSolucao; }
}