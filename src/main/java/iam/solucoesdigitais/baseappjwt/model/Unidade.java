package iam.solucoesdigitais.baseappjwt.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unidades")
public class Unidade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nome;
    
    @OneToMany(mappedBy = "unidade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Setor> setores = new ArrayList<>();
    
    @OneToMany(mappedBy = "unidade")
    private List<Material> materiais = new ArrayList<>();
    
    @OneToMany(mappedBy = "unidade")
    private List<Chamado> chamados = new ArrayList<>();
    
    // Construtores
    public Unidade() {}
    
    public Unidade(String nome) {
        this.nome = nome;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public List<Setor> getSetores() { return setores; }
    public void setSetores(List<Setor> setores) { this.setores = setores; }
    
    public List<Material> getMateriais() { return materiais; }
    public void setMateriais(List<Material> materiais) { this.materiais = materiais; }
    
    public List<Chamado> getChamados() { return chamados; }
    public void setChamados(List<Chamado> chamados) { this.chamados = chamados; }
    
    // Métodos auxiliares
    public void adicionarSetor(Setor setor) {
        setores.add(setor);
        setor.setUnidade(this);
    }
    
    public void removerSetor(Setor setor) {
        setores.remove(setor);
        setor.setUnidade(null);
    }
}
