package iam.solucoesdigitais.baseappjwt.dto;

import java.time.LocalDateTime;

public class DefinicaoPorteDTO {

    private Long id;
    private String cnpjCpfEmpresa;
    private Long empresaId;
    private Double quantidadeGerada;
    private String porte;
    private String responsavelTipo;
    private Long responsavelId;
    private String responsavelNome;
    private String registradoPor;
    private LocalDateTime dataRegistro;
    private String cpfDoRegistrador;
    private Long usuarioId;

    // Constructors
    public DefinicaoPorteDTO() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpjCpfEmpresa() {
        return cnpjCpfEmpresa;
    }

    public void setCnpjCpfEmpresa(String cnpjCpfEmpresa) {
        this.cnpjCpfEmpresa = cnpjCpfEmpresa;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public Double getQuantidadeGerada() {
        return quantidadeGerada;
    }

    public void setQuantidadeGerada(Double quantidadeGerada) {
        this.quantidadeGerada = quantidadeGerada;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getResponsavelTipo() {
        return responsavelTipo;
    }

    public void setResponsavelTipo(String responsavelTipo) {
        this.responsavelTipo = responsavelTipo;
    }

    public Long getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(Long responsavelId) {
        this.responsavelId = responsavelId;
    }

    public String getResponsavelNome() {
        return responsavelNome;
    }

    public void setResponsavelNome(String responsavelNome) {
        this.responsavelNome = responsavelNome;
    }

    public String getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(String registradoPor) {
        this.registradoPor = registradoPor;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getCpfDoRegistrador() {
        return cpfDoRegistrador;
    }

    public void setCpfDoRegistrador(String cpfDoRegistrador) {
        this.cpfDoRegistrador = cpfDoRegistrador;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
