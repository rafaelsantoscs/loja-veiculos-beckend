package iam.solucoesdigitais.baseappjwt.dto;

import java.time.LocalDateTime;

public class SubtarefaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private boolean concluida;
    private LocalDateTime dataConclusao;
    private String concluidaPor;
    private Integer ordem;

    // Construtores
    public SubtarefaDTO() {}

    public SubtarefaDTO(Long id, String titulo, String descricao, boolean concluida, 
                        LocalDateTime dataConclusao, String concluidaPor, Integer ordem) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = concluida;
        this.dataConclusao = dataConclusao;
        this.concluidaPor = concluidaPor;
        this.ordem = ordem;
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
}
