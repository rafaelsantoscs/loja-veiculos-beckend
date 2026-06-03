package iam.solucoesdigitais.baseappjwt.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import iam.solucoesdigitais.baseappjwt.model.Material;
import iam.solucoesdigitais.enums.TipoMaterial;
import iam.solucoesdigitais.enums.StatusMaterial;

public class MaterialDTO {
    
    private Long id;
    private TipoMaterial tipo;
    private String tombamento;
    private StatusMaterial status;
    private Long unidadeId;
    private String unidadeNome;
    private Long setorId;
    private String setorNome;
    private String marca;
    private String especificacoes;
    private String cadastradoPor;
    private String dataCadastro;
    private Integer quantidadeChamados;
    private Integer quantidadeManutencoes;
    
    // Construtores
    public MaterialDTO() {}
    
    public MaterialDTO(Material material) {
        this.id = material.getId();
        this.tipo = material.getTipo();
        this.tombamento = material.getTombamento();
        this.status = material.getStatus();
        this.marca = material.getMarca();
        this.especificacoes = material.getEspecificacoes();
        this.cadastradoPor = material.getCadastradoPor();
        this.quantidadeChamados = material.getChamados() != null ? material.getChamados().size() : 0;
        this.quantidadeManutencoes = material.getManutencoes() != null ? material.getManutencoes().size() : 0;
        
        // Formatar data
        if (material.getDataCadastro() != null) {
            this.dataCadastro = material.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        }
        
        // Relacionamentos
        if (material.getUnidade() != null) {
            this.unidadeId = material.getUnidade().getId();
            this.unidadeNome = material.getUnidade().getNome();
        }
        
        if (material.getSetor() != null) {
            this.setorId = material.getSetor().getId();
            this.setorNome = material.getSetor().getNome();
        }
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public TipoMaterial getTipo() { return tipo; }
    public void setTipo(TipoMaterial tipo) { this.tipo = tipo; }
    
    public String getTombamento() { return tombamento; }
    public void setTombamento(String tombamento) { this.tombamento = tombamento; }
    
    public StatusMaterial getStatus() { return status; }
    public void setStatus(StatusMaterial status) { this.status = status; }
    
    public Long getUnidadeId() { return unidadeId; }
    public void setUnidadeId(Long unidadeId) { this.unidadeId = unidadeId; }
    
    public String getUnidadeNome() { return unidadeNome; }
    public void setUnidadeNome(String unidadeNome) { this.unidadeNome = unidadeNome; }
    
    public Long getSetorId() { return setorId; }
    public void setSetorId(Long setorId) { this.setorId = setorId; }
    
    public String getSetorNome() { return setorNome; }
    public void setSetorNome(String setorNome) { this.setorNome = setorNome; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public String getEspecificacoes() { return especificacoes; }
    public void setEspecificacoes(String especificacoes) { this.especificacoes = especificacoes; }
    
    public String getCadastradoPor() { return cadastradoPor; }
    public void setCadastradoPor(String cadastradoPor) { this.cadastradoPor = cadastradoPor; }
    
    public String getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(String dataCadastro) { this.dataCadastro = dataCadastro; }
    
    public Integer getQuantidadeChamados() { return quantidadeChamados; }
    public void setQuantidadeChamados(Integer quantidadeChamados) { this.quantidadeChamados = quantidadeChamados; }
    
    public Integer getQuantidadeManutencoes() { return quantidadeManutencoes; }
    public void setQuantidadeManutencoes(Integer quantidadeManutencoes) { this.quantidadeManutencoes = quantidadeManutencoes; }
}