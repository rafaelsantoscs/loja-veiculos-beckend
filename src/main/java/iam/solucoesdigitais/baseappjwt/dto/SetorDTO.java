package iam.solucoesdigitais.baseappjwt.dto;

import iam.solucoesdigitais.baseappjwt.model.Setor;

public class SetorDTO {
    
    private Long id;
    private String nome;
    private Long unidadeId;
    private String unidadeNome;
    private Integer quantidadeMateriais;
    private Integer quantidadeChamados;
    
    // Construtores
    public SetorDTO() {}
    
    public SetorDTO(Long id, String nome, Long unidadeId, String unidadeNome) {
        this.id = id;
        this.nome = nome;
        this.unidadeId = unidadeId;
        this.unidadeNome = unidadeNome;
    }
    
    public SetorDTO(Setor setor) {
        this.id = setor.getId();
        this.nome = setor.getNome();
        this.quantidadeMateriais = setor.getMateriais() != null ? setor.getMateriais().size() : 0;
        this.quantidadeChamados = setor.getChamados() != null ? setor.getChamados().size() : 0;
        
        if (setor.getUnidade() != null) {
            this.unidadeId = setor.getUnidade().getId();
            this.unidadeNome = setor.getUnidade().getNome();
        }
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Long getUnidadeId() { return unidadeId; }
    public void setUnidadeId(Long unidadeId) { this.unidadeId = unidadeId; }
    
    public String getUnidadeNome() { return unidadeNome; }
    public void setUnidadeNome(String unidadeNome) { this.unidadeNome = unidadeNome; }
    
    public Integer getQuantidadeMateriais() { return quantidadeMateriais; }
    public void setQuantidadeMateriais(Integer quantidadeMateriais) { this.quantidadeMateriais = quantidadeMateriais; }
    
    public Integer getQuantidadeChamados() { return quantidadeChamados; }
    public void setQuantidadeChamados(Integer quantidadeChamados) { this.quantidadeChamados = quantidadeChamados; }
}
