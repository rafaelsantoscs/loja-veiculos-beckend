package iam.solucoesdigitais.baseappjwt.model;

import javax.persistence.*;

import iam.solucoesdigitais.enums.StatusAvaliacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Solicitação de avaliação de carro usado enviada pelo cliente
 * (fotos + informações) para a loja avaliar.
 */
@Entity
@Table(name = "avaliacao_veiculo")
public class AvaliacaoVeiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String email;

    @Column
    private String telefone;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column
    private String versao;

    @Column(name = "ano_modelo")
    private Integer anoModelo;

    @Column
    private Long quilometragem;

    @Column(name = "valor_pretendido", precision = 12, scale = 2)
    private BigDecimal valorPretendido;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAvaliacao status = StatusAvaliacao.PENDENTE;

    @Column(name = "data_envio", nullable = false)
    private LocalDateTime dataEnvio;

    @OneToMany(
            mappedBy = "avaliacao",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FotoAvaliacao> fotos = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (dataEnvio == null) {
            dataEnvio = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public Integer getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
    }

    public Long getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Long quilometragem) {
        this.quilometragem = quilometragem;
    }

    public BigDecimal getValorPretendido() {
        return valorPretendido;
    }

    public void setValorPretendido(BigDecimal valorPretendido) {
        this.valorPretendido = valorPretendido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusAvaliacao getStatus() {
        return status;
    }

    public void setStatus(StatusAvaliacao status) {
        this.status = status;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public List<FotoAvaliacao> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotoAvaliacao> fotos) {
        this.fotos = fotos;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AvaliacaoVeiculo)) return false;
        AvaliacaoVeiculo other = (AvaliacaoVeiculo) obj;
        return id != null && id.equals(other.id);
    }
}
