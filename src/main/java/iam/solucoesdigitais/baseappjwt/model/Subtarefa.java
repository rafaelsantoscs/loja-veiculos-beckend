package iam.solucoesdigitais.baseappjwt.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;

@Entity
@Table(name = "subtarefas")
public class Subtarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private boolean concluida = false;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @Column(name = "concluida_por")
    private String concluidaPor;

    @Column(nullable = false)
    private Integer ordem = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarefa_id", nullable = false)
    @JsonBackReference
    private Tarefa tarefa;

    // Construtores
    public Subtarefa() {
        this.concluida = false;
        this.ordem = 0;
    }

    public Subtarefa(String titulo, String descricao, Integer ordem, Tarefa tarefa) {
        this();
        this.titulo = titulo;
        this.descricao = descricao;
        if (ordem != null) {
            this.ordem = ordem;
        }
        this.tarefa = tarefa;
    }

    @PrePersist
    protected void onCreate() {
        if (ordem == null) {
            ordem = 0;
        }
    }

    // Método para marcar como concluída
    public void marcarComoConcluida(String usuario) {
        this.concluida = true;
        this.dataConclusao = LocalDateTime.now();
        this.concluidaPor = usuario;
    }

    // Método para desmarcar
    public void desmarcarConclusao() {
        this.concluida = false;
        this.dataConclusao = null;
        this.concluidaPor = null;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public boolean isConcluida() { return concluida; }
    public void setConcluida(boolean concluida) { this.concluida = concluida; }

    public LocalDateTime getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDateTime dataConclusao) { this.dataConclusao = dataConclusao; }

    public String getConcluidaPor() { return concluidaPor; }
    public void setConcluidaPor(String concluidaPor) { this.concluidaPor = concluidaPor; }

    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }

    public Tarefa getTarefa() { return tarefa; }
    public void setTarefa(Tarefa tarefa) { this.tarefa = tarefa; }
}
