package iam.solucoesdigitais.baseappjwt.dto;

public class VeiculoCompletoComDestinoDTO {

    private Long id;
    private String placa;
    private String modelo;
    private String km;
    private String combustivel;
    private String tipo;
    private String motorista;
    private String cpfMotorista;
    private String statusDoVeiculo;
    private String situacao;
    private Long idMapa;
    private String destino;
    private String lotacao;

    // Construtor completo
    public VeiculoCompletoComDestinoDTO(Long id, String placa, String modelo, String km, String combustivel,
                                        String tipo, String motorista, String cpfMotorista,
                                        String statusDoVeiculo, String situacao, Long idMapa, String destino, String lotacao) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.km = km;
        this.combustivel = combustivel;
        this.tipo = tipo;
        this.motorista = motorista;
        this.cpfMotorista = cpfMotorista;
        this.statusDoVeiculo = statusDoVeiculo;
        this.situacao = situacao;
        this.idMapa = idMapa;
        this.destino = destino;
        this.lotacao = lotacao;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getMotorista() {
		return motorista;
	}

	public void setMotorista(String motorista) {
		this.motorista = motorista;
	}

	public String getCpfMotorista() {
		return cpfMotorista;
	}

	public void setCpfMotorista(String cpfMotorista) {
		this.cpfMotorista = cpfMotorista;
	}

	public String getStatusDoVeiculo() {
		return statusDoVeiculo;
	}

	public void setStatusDoVeiculo(String statusDoVeiculo) {
		this.statusDoVeiculo = statusDoVeiculo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Long getIdMapa() {
		return idMapa;
	}

	public void setIdMapa(Long idMapa) {
		this.idMapa = idMapa;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getLotacao() {
		return lotacao;
	}

	public void setLotacao(String lotacao) {
		this.lotacao = lotacao;
	}

    
}
