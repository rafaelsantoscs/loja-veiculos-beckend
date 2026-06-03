package iam.solucoesdigitais.baseappjwt.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class TarefaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String criadoPor; // Email do criador
    private String criadoPorNome; // Nome do criador
    private String atribuidoPara; // Emails dos responsáveis (separados por vírgula)
    private String atribuidoParaNome; // Nomes dos responsáveis (separados por vírgula)
    private List<String> atribuidoParaEmails; // Lista de emails dos responsáveis
    private List<String> atribuidoParaNomes; // Lista de nomes dos responsáveis
    private LocalDateTime dataCriacao;
    private LocalDateTime dataPrazo;
    private LocalDateTime dataConclusao;
    private String prioridade;
    private String status;
    private String observacoes;
    private List<SubtarefaDTO> subtarefas;
    private Integer progresso;

    // Construtores
    public TarefaDTO() {}

    public TarefaDTO(Long id, String titulo, String descricao, String criadoPor, String atribuidoPara,
                     LocalDateTime dataCriacao, LocalDateTime dataPrazo, LocalDateTime dataConclusao,
                     String prioridade, String status, String observacoes, 
                     List<SubtarefaDTO> subtarefas, Integer progresso) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.criadoPor = criadoPor;
        this.atribuidoPara = atribuidoPara;
        this.dataCriacao = dataCriacao;
        this.dataPrazo = dataPrazo;
        this.dataConclusao = dataConclusao;
        this.prioridade = prioridade;
        this.status = status;
        this.observacoes = observacoes;
        this.subtarefas = subtarefas;
        this.progresso = progresso;
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

    public String getCriadoPorNome() { return criadoPorNome; }
    public void setCriadoPorNome(String criadoPorNome) { this.criadoPorNome = criadoPorNome; }

    public String getAtribuidoPara() { return atribuidoPara; }
    public void setAtribuidoPara(String atribuidoPara) { this.atribuidoPara = atribuidoPara; }

    public String getAtribuidoParaNome() { return atribuidoParaNome; }
    public void setAtribuidoParaNome(String atribuidoParaNome) { this.atribuidoParaNome = atribuidoParaNome; }

    public List<String> getAtribuidoParaEmails() { return atribuidoParaEmails; }
    public void setAtribuidoParaEmails(List<String> atribuidoParaEmails) { this.atribuidoParaEmails = atribuidoParaEmails; }

    public List<String> getAtribuidoParaNomes() { return atribuidoParaNomes; }
    public void setAtribuidoParaNomes(List<String> atribuidoParaNomes) { this.atribuidoParaNomes = atribuidoParaNomes; }

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

    public List<SubtarefaDTO> getSubtarefas() { return subtarefas; }
    public void setSubtarefas(List<SubtarefaDTO> subtarefas) { this.subtarefas = subtarefas; }

    public Integer getProgresso() { return progresso; }
    public void setProgresso(Integer progresso) { this.progresso = progresso; }
}
