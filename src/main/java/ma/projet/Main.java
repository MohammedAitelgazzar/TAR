package ma.projet;

import ma.projet.classes.Categorie;
import ma.projet.classes.Produit;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.service.CategorieService;
import ma.projet.service.ProduitService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneCommandeService = new LigneCommandeService();

        Categorie categorie = new Categorie("ELEC", "Electronics", new ArrayList<>());
        if (categorieService.create(categorie)) {
            System.out.println("Categorie created: " + categorie.getLibelle());
        } else {
            System.out.println("Failed to create categorie.");
            return;
        }

        Produit produit = new Produit("Laptop", 1500.0f, categorie, new ArrayList<>());
        if (produitService.create(produit)) {
            System.out.println("Produit created: " + produit.getReference());
        } else {
            System.out.println("Failed to create produit.");
            return;
        }

        Commande commande = new Commande(new Date(), new ArrayList<>());
        if (commandeService.create(commande)) {
            System.out.println("Commande created with ID: " + commande.getId());
        } else {
            System.out.println("Failed to create commande.");
            return;
        }

        LigneCommandeProduit ligneCommandeProduit = new LigneCommandeProduit(commande, produit, 2); // 2 est la quantité
        if (ligneCommandeService.create(ligneCommandeProduit)) {
            System.out.println("LigneCommandeProduit created: Produit " + produit.getReference() + " avec quantité " + ligneCommandeProduit.getQuantite());
        } else {
            System.out.println("Failed to create LigneCommandeProduit.");
        }
    }
}
