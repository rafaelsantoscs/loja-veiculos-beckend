package iam.solucoesdigitais.baseappjwt.dto;

public class ItemRecebidoMotoristaDTO {
    private Long itemId;
    private String novaSituacao;
    private String dataDaNovaSituacao;
    private String observacaoConclusao;
    
    public ItemRecebidoMotoristaDTO() {}
    
    public ItemRecebidoMotoristaDTO(Long itemId, String novaSituacao, String dataDaNovaSituacao, String observacaoConclusao) {
        this.itemId = itemId;
        this.novaSituacao = novaSituacao;
        this.dataDaNovaSituacao = dataDaNovaSituacao;
        this.observacaoConclusao = observacaoConclusao;
    }

    // Getters e Setters
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public String getObservacaoConclusao() {
        return observacaoConclusao;
    }

    public void setObservacaoConclusao(String observacaoConclusao) {
        this.observacaoConclusao = observacaoConclusao;
    }
}