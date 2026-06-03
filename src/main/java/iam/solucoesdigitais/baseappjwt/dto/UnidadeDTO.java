package iam.solucoesdigitais.baseappjwt.dto;

import java.util.List;
import java.util.stream.Collectors;

import iam.solucoesdigitais.baseappjwt.model.Unidade;
import iam.solucoesdigitais.baseappjwt.model.Setor;

public class UnidadeDTO {
    
    private Long id;
    private String nome;
    private List<SetorDTO> setores;
    private Integer quantidadeMateriais;
    private Integer quantidadeChamados;
    
    // Construtores
    public UnidadeDTO() {}
    
    public UnidadeDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    public UnidadeDTO(Unidade unidade) {
        this.id = unidade.getId();
        this.nome = unidade.getNome();
        this.quantidadeMateriais = unidade.getMateriais() != null ? unidade.getMateriais().size() : 0;
        this.quantidadeChamados = unidade.getChamados() != null ? unidade.getChamados().size() : 0;
        
        if (unidade.getSetores() != null) {
            this.setores = unidade.getSetores().stream()
                .map(SetorDTO::new)
                .collect(Collectors.toList());
        }
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public List<SetorDTO> getSetores() { return setores; }
    public void setSetores(List<SetorDTO> setores) { this.setores = setores; }
    
    public Integer getQuantidadeMateriais() { return quantidadeMateriais; }
    public void setQuantidadeMateriais(Integer quantidadeMateriais) { this.quantidadeMateriais = quantidadeMateriais; }
    
    public Integer getQuantidadeChamados() { return quantidadeChamados; }
    public void setQuantidadeChamados(Integer quantidadeChamados) { this.quantidadeChamados = quantidadeChamados; }
}
