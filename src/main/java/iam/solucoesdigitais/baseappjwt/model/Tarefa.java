package iam.solucoesdigitais.baseappjwt.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "criado_por", nullable = false)
    private String criadoPor;

    @Column(name = "atribuido_para", nullable = false)
    private String atribuidoPara; // Email do usuário

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_prazo")
    private LocalDateTime dataPrazo;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @Column(nullable = false)
    private String prioridade; // BAIXA, MEDIA, ALTA, URGENTE

    @Column(nullable = false)
    private String status; // PENDENTE, EM_ANDAMENTO, CONCLUIDA, CANCELADA

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Subtarefa> subtarefas = new ArrayList<>();

    // Construtores
    public Tarefa() {
        this.dataCriacao = LocalDateTime.now();
        this.status = "PENDENTE";
        this.prioridade = "MEDIA";
    }

    public Tarefa(String titulo, String descricao, String criadoPor, String atribuidoPara, 
                  LocalDateTime dataPrazo, String prioridade) {
        this();
        this.titulo = titulo;
        this.descricao = descricao;
        this.criadoPor = criadoPor;
        this.atribuidoPara = atribuidoPara;
        this.dataPrazo = dataPrazo;
        if (prioridade != null) {
            this.prioridade = prioridade;
        }
    }

    @PrePersist
    protected void onCreate() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
        if (status == null) {
            status = "PENDENTE";
        }
        if (prioridade == null) {
            prioridade = "MEDIA";
        }
    }

    // Método auxiliar para calcular progresso
    @Transient
    public int getProgresso() {
        if (subtarefas == null || subtarefas.isEmpty()) {
            return "CONCLUIDA".equals(status) ? 100 : 0;
        }
        long concluidas = subtarefas.stream()
            .filter(s -> s.isConcluida())
            .count();
        return (int) ((concluidas * 100.0) / subtarefas.size());
    }

    // Método para verificar se todas as subtarefas estão concluídas
    @Transient
    public boolean todasSubtarefasConcluidas() {
        if (subtarefas == null || subtarefas.isEmpty()) {
            return false;
        }
        return subtarefas.stream().allMatch(Subtarefa::isConcluida);
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCriadoPor() { return criadoPor; }
    public void setCriadoPor(String criadoPor) { this.criadoPor = criadoPor; }

    public String getAtribuidoPara() { return atribuidoPara; }
    public void setAtribuidoPara(String atribuidoPara) { this.atribuidoPara = atribuidoPara; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataPrazo() { return dataPrazo; }
    public void setDataPrazo(LocalDateTime dataPrazo) { this.dataPrazo = dataPrazo; }

    public LocalDateTime getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDateTime dataConclusao) { this.dataConclusao = dataConclusao; }

    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public List<Subtarefa> getSubtarefas() { return subtarefas; }
    public void setSubtarefas(List<Subtarefa> subtarefas) { this.subtarefas = subtarefas; }
}
