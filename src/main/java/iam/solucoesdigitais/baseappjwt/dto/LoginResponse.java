package iam.solucoesdigitais.baseappjwt.dto;


public class LoginResponse {
    private String token;
    private String nome;
    private String username;
    private String cpf;
    private String email;
    private String telefone;
    private String imagemUrl;
    private Long id;
    
    // Construtor
    public LoginResponse(String token, String nome, String username, String cpf, String email, String telefone, String imagemUrl, Long id) {
        this.token = token;
        this.nome = nome;
        this.username = username;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.imagemUrl = imagemUrl;
        this.id = id;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    
}