package iam.solucoesdigitais.baseappjwt.model;

import javax.persistence.*;

@Entity
@Table(name = "foto_avaliacao")
public class FotoAvaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_arquivo", nullable = false)
    private String nomeArquivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliacao_id", nullable = false)
    private AvaliacaoVeiculo avaliacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public AvaliacaoVeiculo getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(AvaliacaoVeiculo avaliacao) {
        this.avaliacao = avaliacao;
    }
}
