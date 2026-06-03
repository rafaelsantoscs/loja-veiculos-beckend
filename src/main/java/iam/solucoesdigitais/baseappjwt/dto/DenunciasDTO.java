package iam.solucoesdigitais.baseappjwt.dto;

import java.time.LocalDateTime;
import java.util.List;

import iam.solucoesdigitais.enums.StatusDenuncia;

public class DenunciasDTO {

    private Long id;
    private String denunciante;
    private String denunciado;
    private String nomeRua;
    private String numero;
    private String cidade;
    private String bairro;
    private String motivo;
    private LocalDateTime data;
    private LocalDateTime dataCriacao;
    private String protocolo;
    private String status; // Mudando para String para facilitar a serialização
    private List<StatusHistoricoDTO> statusHistory;

    // Construtores
    public DenunciasDTO() {}

    public DenunciasDTO(Long id, String denunciante, String denunciado,
                       String nomeRua, String numero, String cidade,
                       String bairro, String motivo, LocalDateTime data, String protocolo, String status) {
        this.id = id;
        this.denunciante = denunciante;
        this.denunciado = denunciado;
        this.nomeRua = nomeRua;
        this.numero = numero;
        this.cidade = cidade;
        this.bairro = bairro;
        this.motivo = motivo;
        this.data = data;
        this.dataCriacao = data;
        this.protocolo = protocolo;
        this.status = status;
    }

    // Getters e Setters
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

	public String getProtocolo() {
    	return protocolo;
    }
    public void setProtocolo(String protocolo) {
    	this.protocolo = protocolo;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDenunciante() {
        return denunciante;
    }
    public void setDenunciante(String denunciante) {
        this.denunciante = denunciante;
    }

    public String getDenunciado() {
        return denunciado;
    }
    public void setDenunciado(String denunciado) {
        this.denunciado = denunciado;
    }

    public String getNomeRua() {
        return nomeRua;
    }
    public void setNomeRua(String nomeRua) {
        this.nomeRua = nomeRua;
    }

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public List<StatusHistoricoDTO> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<StatusHistoricoDTO> statusHistory) {
        this.statusHistory = statusHistory;
    }
}