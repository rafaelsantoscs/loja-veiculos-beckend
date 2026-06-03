package iam.solucoesdigitais.baseappjwt.dto;

public class ItemChecklistMotoristaDTO {

	private Long id;
	private Long idItem;
    private String nomeItem;
    private String sessao;
    private String situacao;
    private String novaSituacao;
    private String dataDaNovaSituacao;
    
    
    public ItemChecklistMotoristaDTO() {}
    
	public ItemChecklistMotoristaDTO(Long id, Long idItem, String nomeItem, String sessao, String situacao, String novaSituacao, String dataDaNovaSituacao) {
		super();
		this.id = id;
		this.idItem = idItem;
		this.nomeItem = nomeItem;
		this.sessao = sessao;
		this.situacao = situacao;
		this.novaSituacao = novaSituacao;
		this.dataDaNovaSituacao = dataDaNovaSituacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public String getNomeItem() {
		return nomeItem;
	}

	public void setNomeItem(String nomeItem) {
		this.nomeItem = nomeItem;
	}
	
	public String getSessao() {
		return sessao;
	}

	public void setSessao(String sessao) {
		this.sessao = sessao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getNovaSituacao() {
		return novaSituacao;
	}

	public void setNovaSituacao(String novaSituacao) {
		this.novaSituacao = novaSituacao;
	}

	public String getDataDaNovaSituacao() {
		return dataDaNovaSituacao;
	}

	public void setDataDaNovaSituacao(String dataDaNovaSituacao) {
		this.dataDaNovaSituacao = dataDaNovaSituacao;
	}

}
