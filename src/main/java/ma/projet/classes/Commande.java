package ma.projet.classes;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;
    @OneToMany(mappedBy = "commande")
    private List<LigneCommandeProduit> ligneCommandeProduits;

    public Commande() {
    }

    public Commande(Date date, List<LigneCommandeProduit> ligneCommandeProduits) {
        this.date = date;
        this.ligneCommandeProduits = ligneCommandeProduits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
