package iam.solucoesdigitais.baseappjwt.model;

import javax.persistence.*;

import iam.solucoesdigitais.enums.StatusChamado;

import java.time.LocalDateTime;

@Entity
@Table(name = "chamados")
public class Chamado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "usuario_abertura", nullable = false)
    private String usuarioAbertura;
    
    @Column(name = "data_abertura", nullable = false)
    private LocalDateTime dataAbertura;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id", nullable = false)
    private Setor setor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status;
    
    @Column(nullable = false, unique = true)
    private String protocolo;
    
    @Column(name = "descricao_problema", columnDefinition = "TEXT", nullable = false)
    private String descricaoProblema;
    
    @Column(name = "parecer_suporte", columnDefinition = "TEXT")
    private String parecerSuporte;
    
    // Construtores
    public Chamado() {
        this.dataAbertura = LocalDateTime.now();
        this.status = StatusChamado.ABERTO;
    }
    
    public Chamado(String usuarioAbertura, Unidade unidade, Setor setor, 
                   Material material, String protocolo, String descricaoProblema) {
        this();
        this.usuarioAbertura = usuarioAbertura;
        this.unidade = unidade;
        this.setor = setor;
        this.material = material;
        this.protocolo = protocolo;
        this.descricaoProblema = descricaoProblema;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsuarioAbertura() { return usuarioAbertura; }
    public void setUsuarioAbertura(String usuarioAbertura) { this.usuarioAbertura = usuarioAbertura; }
    
    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }
    
    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }
    
    public Setor getSetor() { return setor; }
    public void setSetor(Setor setor) { this.setor = setor; }
    
    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }
    
    public StatusChamado getStatus() { return status; }
    public void setStatus(StatusChamado status) { this.status = status; }
    
    public String getProtocolo() { return protocolo; }
    public void setProtocolo(String protocolo) { this.protocolo = protocolo; }
    
    public String getDescricaoProblema() { return descricaoProblema; }
    public void setDescricaoProblema(String descricaoProblema) { this.descricaoProblema = descricaoProblema; }
    
    public String getParecerSuporte() { return parecerSuporte; }
    public void setParecerSuporte(String parecerSuporte) { this.parecerSuporte = parecerSuporte; }
}
