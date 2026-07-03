package iam.solucoesdigitais.baseappjwt.dto;

import iam.solucoesdigitais.enums.CategoriaDespesa;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DespesaRequestDTO {

    private CategoriaDespesa categoria;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private Long veiculoId;

    public CategoriaDespesa getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDespesa categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }
}
