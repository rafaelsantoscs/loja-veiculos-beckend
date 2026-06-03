package iam.solucoesdigitais.baseappjwt.dto;

public class DataHoraServidorDTO {
    private String data;
    private String hora;

    public DataHoraServidorDTO(String data, String hora) {
        this.data = data;
        this.hora = hora;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
