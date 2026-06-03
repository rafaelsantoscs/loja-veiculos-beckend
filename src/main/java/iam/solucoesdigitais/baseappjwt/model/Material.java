package iam.solucoesdigitais.baseappjwt.model;

import javax.persistence.*;

import iam.solucoesdigitais.enums.StatusMaterial;
import iam.solucoesdigitais.enums.TipoMaterial;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materiais")
public class Material {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMaterial tipo;
    
    @Column(nullable = false, unique = true)
    private String tombamento;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMaterial status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id", nullable = false)
    private Setor setor;
    
    @Column(nullable = false)
    private String marca;
    
    @Column(columnDefinition = "TEXT")
    private String especificacoes;
    
    @Column(name = "cadastrado_por", nullable = false)
    private String cadastradoPor;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @OneToMany(mappedBy = "material")
    private List<Chamado> chamados = new ArrayList<>();
    
    @OneToMany(mappedBy = "material")
    private List<Manutencao> manutencoes = new ArrayList<>();
    
    // Construtores
    public Material() {
        this.dataCadastro = LocalDateTime.now();
    }
    
    public Material(TipoMaterial tipo, String tombamento, StatusMaterial status, 
                   Unidade unidade, Setor setor, String marca, String cadastradoPor) {
        this();
        this.tipo = tipo;
        this.tombamento = tombamento;
        this.status = status;
        this.unidade = unidade;
        this.setor = setor;
        this.marca = marca;
        this.cadastradoPor = cadastradoPor;
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
    
    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }
    
    public Setor getSetor() { return setor; }
    public void setSetor(Setor setor) { this.setor = setor; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public String getEspecificacoes() { return especificacoes; }
    public void setEspecificacoes(String especificacoes) { this.especificacoes = especificacoes; }
    
    public String getCadastradoPor() { return cadastradoPor; }
    public void setCadastradoPor(String cadastradoPor) { this.cadastradoPor = cadastradoPor; }
    
    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }
    
    public List<Chamado> getChamados() { return chamados; }
    public void setChamados(List<Chamado> chamados) { this.chamados = chamados; }
    
    public List<Manutencao> getManutencoes() { return manutencoes; }
    public void setManutencoes(List<Manutencao> manutencoes) { this.manutencoes = manutencoes; }
}