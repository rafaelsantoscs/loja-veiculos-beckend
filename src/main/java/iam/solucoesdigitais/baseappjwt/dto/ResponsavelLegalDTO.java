package iam.solucoesdigitais.baseappjwt.dto;

import java.time.LocalDateTime;

public class ResponsavelLegalDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String cnpjCpfEmpresa;
    private String telefone1;
    private String telefone2;
    private String email;
    private String registradoPor;
    private LocalDateTime dataRegistro;
    private String cpfDoRegistrador;
    private Long usuarioId;
    private Long empresaId;

    // Constructors
    public ResponsavelLegalDTO() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpjCpfEmpresa() {
        return cnpjCpfEmpresa;
    }

    public void setCnpjCpfEmpresa(String cnpjCpfEmpresa) {
        this.cnpjCpfEmpresa = cnpjCpfEmpresa;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }
}
