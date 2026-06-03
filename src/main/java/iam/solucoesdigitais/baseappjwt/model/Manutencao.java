package iam.solucoesdigitais.baseappjwt.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "manutencoes")
public class Manutencao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
    
    @Column(name = "chamado_id")
    private Long chamadoId;
    
    @Column(name = "data_abertura", nullable = false)
    private LocalDateTime dataAbertura;
    
    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;
    
    @Column(name = "descricao_problema", columnDefinition = "TEXT", nullable = false)
    private String descricaoProblema;
    
    @Column(name = "descricao_solucao", columnDefinition = "TEXT")
    private String descricaoSolucao;
    
    @Column(nullable = false)
    private String tecnico;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusManutencao status;
    
    // Enum interno para status da manutenção
    public enum StatusManutencao {
        ABERTA,
        FECHADA
    }
    
    // Construtores
    public Manutencao() {
        this.dataAbertura = LocalDateTime.now();
        this.status = StatusManutencao.ABERTA;
    }
    
    public Manutencao(Material material, String descricaoProblema, String tecnico) {
        this();
        this.material = material;
        this.descricaoProblema = descricaoProblema;
        this.tecnico = tecnico;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }
    
    public Long getChamadoId() { return chamadoId; }
    public void setChamadoId(Long chamadoId) { this.chamadoId = chamadoId; }
    
    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }
    
    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(LocalDateTime dataFechamento) { this.dataFechamento = dataFechamento; }
    
    public String getDescricaoProblema() { return descricaoProblema; }
    public void setDescricaoProblema(String descricaoProblema) { this.descricaoProblema = descricaoProblema; }
    
    public String getDescricaoSolucao() { return descricaoSolucao; }
    public void setDescricaoSolucao(String descricaoSolucao) { this.descricaoSolucao = descricaoSolucao; }
    
    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }
    
    public StatusManutencao getStatus() { return status; }
    public void setStatus(StatusManutencao status) { this.status = status; }
    
    // Método para fechar a manutenção
    public void fecharManutencao(String descricaoSolucao) {
        this.status = StatusManutencao.FECHADA;
        this.descricaoSolucao = descricaoSolucao;
        this.dataFechamento = LocalDateTime.now();
    }
}
