package iam.solucoesdigitais.baseappjwt.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @Column(nullable = false)
    private LocalDateTime expiraEm;

    // Construtor vazio
    public ConfirmationToken() {}

    public ConfirmationToken(String token, Usuario usuario, LocalDateTime criadoEm, LocalDateTime expiraEm) {
        this.token = token;
        this.usuario = usuario;
        this.criadoEm = criadoEm;
        this.expiraEm = expiraEm;
    }

	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}

	public LocalDateTime getExpiraEm() {
		return expiraEm;
	}

	public void setExpiraEm(LocalDateTime expiraEm) {
		this.expiraEm = expiraEm;
	}

    // Getters e Setters
    // ...
    
    
}
