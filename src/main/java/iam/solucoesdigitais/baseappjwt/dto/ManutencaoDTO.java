package iam.solucoesdigitais.baseappjwt.dto;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import iam.solucoesdigitais.baseappjwt.model.Manutencao;
public class ManutencaoDTO {
    
    private Long id;
    private Long materialId;
    private Long chamadoId;
    private String materialTombamento;
    private String materialTipo;
    private String materialMarca;
    private String dataAbertura;
    private String dataFechamento;
    private String descricaoProblema;
    private String descricaoSolucao;
    private String tecnico;
    private Manutencao.StatusManutencao status;
    
    // Construtores
    public ManutencaoDTO() {}
    
    public ManutencaoDTO(Manutencao manutencao) {
        this.id = manutencao.getId();
        this.chamadoId = manutencao.getChamadoId();
        this.descricaoProblema = manutencao.getDescricaoProblema();
        this.descricaoSolucao = manutencao.getDescricaoSolucao();
        this.tecnico = manutencao.getTecnico();
        this.status = manutencao.getStatus();
        
        // Formatar datas
        if (manutencao.getDataAbertura() != null) {
            this.dataAbertura = manutencao.getDataAbertura().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        }
        
        if (manutencao.getDataFechamento() != null) {
            this.dataFechamento = manutencao.getDataFechamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        }
        
        // Relacionamento com material
        if (manutencao.getMaterial() != null) {
            this.materialId = manutencao.getMaterial().getId();
            this.materialTombamento = manutencao.getMaterial().getTombamento();
            this.materialTipo = manutencao.getMaterial().getTipo().toString();
            this.materialMarca = manutencao.getMaterial().getMarca();
        }
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }
    
    public Long getChamadoId() { return chamadoId; }
    public void setChamadoId(Long chamadoId) { this.chamadoId = chamadoId; }
    
    public String getMaterialTombamento() { return materialTombamento; }
    public void setMaterialTombamento(String materialTombamento) { this.materialTombamento = materialTombamento; }
    
    public String getMaterialTipo() { return materialTipo; }
    public void setMaterialTipo(String materialTipo) { this.materialTipo = materialTipo; }
    
    public String getMaterialMarca() { return materialMarca; }
    public void setMaterialMarca(String materialMarca) { this.materialMarca = materialMarca; }
    
    public String getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(String dataAbertura) { this.dataAbertura = dataAbertura; }
    
    public String getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(String dataFechamento) { this.dataFechamento = dataFechamento; }
    
    public String getDescricaoProblema() { return descricaoProblema; }
    public void setDescricaoProblema(String descricaoProblema) { this.descricaoProblema = descricaoProblema; }
    
    public String getDescricaoSolucao() { return descricaoSolucao; }
    public void setDescricaoSolucao(String descricaoSolucao) { this.descricaoSolucao = descricaoSolucao; }
    
    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }
    
    public Manutencao.StatusManutencao getStatus() { return status; }
    public void setStatus(Manutencao.StatusManutencao status) { this.status = status; }
}