package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQuery(name = "Produit.findByPriceGreaterThan100", query = "FROM Produit p WHERE p.prix > 100")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reference;
    private float prix;
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    public Categorie categorie;
    @OneToMany(mappedBy = "produit")
    private List<LigneCommandeProduit> ligneCommandeProduits;

    public Produit() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Produit( String reference, float prix, Categorie categorie, List<LigneCommandeProduit> ligneCommandeProduits) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
        this.ligneCommandeProduits = ligneCommandeProduits;
    }
}
