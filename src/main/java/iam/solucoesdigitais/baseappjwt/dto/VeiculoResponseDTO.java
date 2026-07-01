package iam.solucoesdigitais.baseappjwt.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import iam.solucoesdigitais.enums.Cambio;
import iam.solucoesdigitais.enums.Combustivel;
import iam.solucoesdigitais.enums.StatusVeiculo;

public class VeiculoResponseDTO {

    private Long id;

    private String marca;
    private String modelo;
    private String versao;

    private Integer anoFabricacao;
    private Integer anoModelo;

    private String placa;
    private String cor;

    private Combustivel combustivel;
    private Cambio cambio;

    private Long quilometragem;

    private BigDecimal precoCompra;
    private BigDecimal precoVenda;

    private String descricao;

    private StatusVeiculo status;
    private Boolean destaque;

    private Boolean aceitaTroca;
    private Boolean unicoDono;
    private Boolean blindado;

    private LocalDate dataEntrada;

    private String fotoPrincipal;

    // ── Getters e Setters ────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getVersao() { return versao; }
    public void setVersao(String versao) { this.versao = versao; }

    public Integer getAnoFabricacao() { return anoFabricacao; }
    public void setAnoFabricacao(Integer anoFabricacao) { this.anoFabricacao = anoFabricacao; }

    public Integer getAnoModelo() { return anoModelo; }
    public void setAnoModelo(Integer anoModelo) { this.anoModelo = anoModelo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public Combustivel getCombustivel() { return combustivel; }
    public void setCombustivel(Combustivel combustivel) { this.combustivel = combustivel; }

    public Cambio getCambio() { return cambio; }
    public void setCambio(Cambio cambio) { this.cambio = cambio; }

    public Long getQuilometragem() { return quilometragem; }
    public void setQuilometragem(Long quilometragem) { this.quilometragem = quilometragem; }

    public BigDecimal getPrecoCompra() { return precoCompra; }
    public void setPrecoCompra(BigDecimal precoCompra) { this.precoCompra = precoCompra; }

    public BigDecimal getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(BigDecimal precoVenda) { this.precoVenda = precoVenda; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public StatusVeiculo getStatus() { return status; }
    public void setStatus(StatusVeiculo status) { this.status = status; }

    public Boolean getDestaque() { return destaque; }
    public void setDestaque(Boolean destaque) { this.destaque = destaque; }

    public Boolean getAceitaTroca() { return aceitaTroca; }
    public void setAceitaTroca(Boolean aceitaTroca) { this.aceitaTroca = aceitaTroca; }

    public Boolean getUnicoDono() { return unicoDono; }
    public void setUnicoDono(Boolean unicoDono) { this.unicoDono = unicoDono; }

    public Boolean getBlindado() { return blindado; }
    public void setBlindado(Boolean blindado) { this.blindado = blindado; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public String getFotoPrincipal() { return fotoPrincipal; }
    public void setFotoPrincipal(String fotoPrincipal) { this.fotoPrincipal = fotoPrincipal; }
}
