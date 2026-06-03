package iam.solucoesdigitais.baseappjwt.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import iam.solucoesdigitais.baseappjwt.model.Chamado;
import iam.solucoesdigitais.enums.StatusChamado;

public class ChamadoDTO {
    
    private Long id;
    private String usuarioAbertura;
    private String dataAbertura;
    private Long unidadeId;
    private String unidadeNome;
    private Long setorId;
    private String setorNome;
    private Long materialId;
    private String materialTombamento;
    private String materialTipo;
    private String materialMarca;
    private StatusChamado status;
    private String protocolo;
    private String descricaoProblema;
    private String parecerSuporte;
    
    // Construtores
    public ChamadoDTO() {}
    
    public ChamadoDTO(Chamado chamado) {
        this.id = chamado.getId();
        this.usuarioAbertura = chamado.getUsuarioAbertura();
        this.status = chamado.getStatus();
        this.protocolo = chamado.getProtocolo();
        this.descricaoProblema = chamado.getDescricaoProblema();
        this.parecerSuporte = chamado.getParecerSuporte();
        
        // Formatar data
        if (chamado.getDataAbertura() != null) {
            this.dataAbertura = chamado.getDataAbertura().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        }
        
        // Relacionamentos
        if (chamado.getUnidade() != null) {
            this.unidadeId = chamado.getUnidade().getId();
            this.unidadeNome = chamado.getUnidade().getNome();
        }
        
        if (chamado.getSetor() != null) {
            this.setorId = chamado.getSetor().getId();
            this.setorNome = chamado.getSetor().getNome();
        }
        
        if (chamado.getMaterial() != null) {
            this.materialId = chamado.getMaterial().getId();
            this.materialTombamento = chamado.getMaterial().getTombamento();
            this.materialTipo = chamado.getMaterial().getTipo().toString();
            this.materialMarca = chamado.getMaterial().getMarca();
        }
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsuarioAbertura() { return usuarioAbertura; }
    public void setUsuarioAbertura(String usuarioAbertura) { this.usuarioAbertura = usuarioAbertura; }
    
    public String getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(String dataAbertura) { this.dataAbertura = dataAbertura; }
    
    public Long getUnidadeId() { return unidadeId; }
    public void setUnidadeId(Long unidadeId) { this.unidadeId = unidadeId; }
    
    public String getUnidadeNome() { return unidadeNome; }
    public void setUnidadeNome(String unidadeNome) { this.unidadeNome = unidadeNome; }
    
    public Long getSetorId() { return setorId; }
    public void setSetorId(Long setorId) { this.setorId = setorId; }
    
    public String getSetorNome() { return setorNome; }
    public void setSetorNome(String setorNome) { this.setorNome = setorNome; }
    
    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }
    
    public String getMaterialTombamento() { return materialTombamento; }
    public void setMaterialTombamento(String materialTombamento) { this.materialTombamento = materialTombamento; }
    
    public String getMaterialTipo() { return materialTipo; }
    public void setMaterialTipo(String materialTipo) { this.materialTipo = materialTipo; }
    
    public String getMaterialMarca() { return materialMarca; }
    public void setMaterialMarca(String materialMarca) { this.materialMarca = materialMarca; }
    
    public StatusChamado getStatus() { return status; }
    public void setStatus(StatusChamado status) { this.status = status; }
    
    public String getProtocolo() { return protocolo; }
    public void setProtocolo(String protocolo) { this.protocolo = protocolo; }
    
    public String getDescricaoProblema() { return descricaoProblema; }
    public void setDescricaoProblema(String descricaoProblema) { this.descricaoProblema = descricaoProblema; }
    
    public String getParecerSuporte() { return parecerSuporte; }
    public void setParecerSuporte(String parecerSuporte) { this.parecerSuporte = parecerSuporte; }
}
