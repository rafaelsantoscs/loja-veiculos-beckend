package iam.solucoesdigitais.baseappjwt.model;

import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefone;
    
    @Column(nullable = false)
    private String termo;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;
    
    @Column(name = "email_confirmado")
    private Boolean emailConfirmado = false;

    
 // Modificação aqui para uma lista de roles
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;  // Lista de papéis (roles)

    // Método para retornar a lista de roles como GrantedAuthority
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                    .map(role -> (GrantedAuthority) () -> role)  // Converte cada role em uma GrantedAuthority
                    .collect(Collectors.toList());
    }

//    @Column(nullable = false)
//    private String role; // Ex: ROLE_ADMIN, ROLE_USER
//
//    // Método para retornar a role como uma lista de GrantedAuthority
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(() -> this.role); // Retorna a role como GrantedAuthority
//    }
    
    @Column(name = "imagem_url")
    private String imagemUrl;

    // Getters e Setters para imagemUrl
    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getters e Setters
    public String getPassword() {
    	return this.password;
    }
    
    public String getUsername() {
    	return this.username;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}

	public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
	public Boolean getEmailConfirmado() {
		return emailConfirmado;
	}

	public void setEmailConfirmado(Boolean emailConfirmado) {
		this.emailConfirmado = emailConfirmado;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
