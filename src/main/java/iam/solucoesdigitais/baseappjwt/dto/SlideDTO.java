package iam.solucoesdigitais.baseappjwt.dto;

public class SlideDTO {

    private String title;
    private String description;
    private String image;
    private Long colaboradorId;

    // Getters e Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Long getColaboradorId() { return colaboradorId; }
    public void setColaboradorId(Long colaboradorId) { this.colaboradorId = colaboradorId; }
}
