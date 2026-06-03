package iam.solucoesdigitais.baseappjwt.dto;

import java.time.LocalDateTime;

public class ResponsavelTecnicoDTO {

    private Long id;
    private String nome;
    private String telefone;
    private String conselhoProfissional;
    private String registroConselho;
    private String email;
    private String cpf;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String cep;
    private String cnpj;
    private String documentoAnexoUrl;
    private String cnpjCpfEmpresa;
    private String registradoPor;
    private LocalDateTime dataRegistro;
    private String cpfDoRegistrador;
    private Long usuarioId;
    private Long empresaId;
    private String status;
    private String observacoes;
    private LocalDateTime ultimaAtualizacao;
    private String atendidoPor;
    private LocalDateTime dataAtendimento;

    // Constructors
    public ResponsavelTecnicoDTO() {}

    public ResponsavelTecnicoDTO(String nome, String telefone, String conselhoProfissional, 
                               String registroConselho, String email, String cnpjCpfEmpresa,
                               Long empresaId) {
        this.nome = nome;
        this.telefone = telefone;
        this.conselhoProfissional = conselhoProfissional;
        this.registroConselho = registroConselho;
        this.email = email;
        this.cnpjCpfEmpresa = cnpjCpfEmpresa;
        this.empresaId = empresaId;
    }

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getConselhoProfissional() {
        return conselhoProfissional;
    }

    public void setConselhoProfissional(String conselhoProfissional) {
        this.conselhoProfissional = conselhoProfissional;
    }

    public String getRegistroConselho() {
        return registroConselho;
    }

    public void setRegistroConselho(String registroConselho) {
        this.registroConselho = registroConselho;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpjCpfEmpresa() {
        return cnpjCpfEmpresa;
    }

    public void setCnpjCpfEmpresa(String cnpjCpfEmpresa) {
        this.cnpjCpfEmpresa = cnpjCpfEmpresa;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getDocumentoAnexoUrl() {
        return documentoAnexoUrl;
    }

    public void setDocumentoAnexoUrl(String documentoAnexoUrl) {
        this.documentoAnexoUrl = documentoAnexoUrl;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getAtendidoPor() {
        return atendidoPor;
    }

    public void setAtendidoPor(String atendidoPor) {
        this.atendidoPor = atendidoPor;
    }

    public LocalDateTime getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDateTime dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }
}
