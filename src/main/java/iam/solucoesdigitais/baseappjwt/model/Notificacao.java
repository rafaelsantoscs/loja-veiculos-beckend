package iam.solucoesdigitais.baseappjwt.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notificacao")
public class Notificacao implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mensagem")
    private String mensagem;
    
    @Column(name = "criado_por")
    private String criadoPor;
    
    @Column(name = "enviado_para")
    private String enviadoPara;
    
    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "momento_criacao")
    private String momentoCriacao;
    
    @Column(name = "momento_recebido")
    private String momentoRecebido;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}

	public String getEnviadoPara() {
		return enviadoPara;
	}
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public void setEnviadoPara(String enviadoPara) {
		this.enviadoPara = enviadoPara;
	}

	public String getMomentoCriacao() {
		return momentoCriacao;
	}

	public void setMomentoCriacao(String momentoCriacao) {
		this.momentoCriacao = momentoCriacao;
	}

	public String getMomentoRecebido() {
		return momentoRecebido;
	}

	public void setMomentoRecebido(String momentoRecebido) {
		this.momentoRecebido = momentoRecebido;
	}

	@Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Notificacao other = (Notificacao) obj;
        return Objects.equals(id, other.id);
    }

	@Override
	public String toString() {
		return "Notificacao [id=" + id + ", mensagem=" + mensagem + ", criadoPor=" + criadoPor + ", momentoCriacao="
				+ momentoCriacao + ", enviadoPara=" + enviadoPara + ", momentoRecebido=" + momentoRecebido + "]";
	}

}