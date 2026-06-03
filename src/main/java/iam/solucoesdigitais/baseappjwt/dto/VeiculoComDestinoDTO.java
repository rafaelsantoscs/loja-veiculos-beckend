package iam.solucoesdigitais.baseappjwt.dto;

public class VeiculoComDestinoDTO extends VeiculoDTO {

    private String destino;

    public VeiculoComDestinoDTO(String placa, String modelo, String km, String combustivel, String tipo, String lotacao, String destino) {
        super(placa, modelo, km, combustivel, tipo, lotacao);
        this.destino = destino;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
