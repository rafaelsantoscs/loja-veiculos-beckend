package iam.solucoesdigitais.baseappjwt.dto;

public class TipoVeiculoResponseDTO {
    private String tipoVeiculo;
    private String tipo;
    private String modelo;
    private String placa;

    // Construtor
    public TipoVeiculoResponseDTO(String tipoVeiculo, String tipo, String modelo, String placa) {
        this.tipoVeiculo = tipoVeiculo;
        this.tipo = tipo;
        this.modelo = modelo;
        this.placa = placa;
    }

    // Getters
    public String getTipoVeiculo() {
        return tipoVeiculo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }
}