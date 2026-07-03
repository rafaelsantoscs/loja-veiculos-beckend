package iam.solucoesdigitais.baseappjwt.model;

import javax.persistence.*;

import iam.solucoesdigitais.enums.Cambio;
import iam.solucoesdigitais.enums.Combustivel;
import iam.solucoesdigitais.enums.OpcionalVeiculo;
import iam.solucoesdigitais.enums.StatusVeiculo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String versao;

    @Column(name = "ano_fabricacao", nullable = false)
    private Integer anoFabricacao;

    @Column(name = "ano_modelo", nullable = false)
    private Integer anoModelo;

    @Column(unique = true, length = 10)
    private String placa;

    @Column(nullable = false)
    private String cor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Combustivel combustivel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cambio cambio;

    @Column(nullable = false)
    private Long quilometragem;

    @Column(name = "preco_compra", precision = 12, scale = 2)
    private BigDecimal precoCompra;

    @Column(name = "preco_venda", nullable = false, precision = 12, scale = 2)
    private BigDecimal precoVenda;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVeiculo status = StatusVeiculo.DISPONIVEL;

    @Column(nullable = false)
    private Boolean destaque = false;

    @Column(nullable = false)
    private Integer visualizacoes = 0;

    @Column(name = "aceita_troca")
    private Boolean aceitaTroca = true;

    @Column(name = "unico_dono")
    private Boolean unicoDono = false;

    @Column(nullable = false)
    private Boolean blindado = false;

    @Column(name = "data_entrada")
    private LocalDate dataEntrada;

    /**
     * Itens opcionais do veículo (ar-condicionado, teto solar, etc).
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "veiculo_opcional",
            joinColumns = @JoinColumn(name = "veiculo_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "opcional")
    private Set<OpcionalVeiculo> opcionais = new HashSet<>();

    @OneToMany(
            mappedBy = "veiculo",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FotoVeiculo> fotos = new ArrayList<>();

    // Getters e Setters

    public List<FotoVeiculo> getFotos() {
		return fotos;
	}

	public void setFotos(List<FotoVeiculo> fotos) {
		this.fotos = fotos;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public Integer getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Combustivel getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(Combustivel combustivel) {
        this.combustivel = combustivel;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public Long getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Long quilometragem) {
        this.quilometragem = quilometragem;
    }

    public BigDecimal getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(BigDecimal precoCompra) {
        this.precoCompra = precoCompra;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    public Boolean getDestaque() {
        return destaque;
    }

    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }

    public Integer getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(Integer visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public Boolean getAceitaTroca() {
        return aceitaTroca;
    }

    public void setAceitaTroca(Boolean aceitaTroca) {
        this.aceitaTroca = aceitaTroca;
    }

    public Boolean getUnicoDono() {
        return unicoDono;
    }

    public void setUnicoDono(Boolean unicoDono) {
        this.unicoDono = unicoDono;
    }

    public Boolean getBlindado() {
        return blindado;
    }

    public void setBlindado(Boolean blindado) {
        this.blindado = blindado;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Set<OpcionalVeiculo> getOpcionais() {
        return opcionais;
    }

    public void setOpcionais(Set<OpcionalVeiculo> opcionais) {
        this.opcionais = opcionais;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Veiculo)) return false;

        Veiculo other = (Veiculo) obj;

        if (id == null) {
            return other.id == null;
        }

        return id.equals(other.id);
    }
}