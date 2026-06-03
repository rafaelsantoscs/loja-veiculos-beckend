package iam.solucoesdigitais.baseappjwt.dto;

public class VeiculoDTO {
    private String placa;
    private String modelo;
    private String km;
    private String combustivel;
    private String tipo;
    private String lotacao;

    // Construtor
    public VeiculoDTO(String placa, String modelo, String km, String combustivel, String tipo, String lotacao) {
        this.placa = placa;
        this.modelo = modelo;
        this.km = km;
        this.combustivel = combustivel;
        this.tipo = tipo;
        this.lotacao = lotacao;
    }

    // Getters e Setters
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLotacao() {
        return lotacao;
    }

    public void setLotacao(String lotacao) {
        this.lotacao = lotacao;
    }
}