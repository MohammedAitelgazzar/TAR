package ma.projet;

import ma.projet.classes.Categorie;
import ma.projet.classes.Produit;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.service.CategorieService;
import ma.projet.service.ProduitService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ma.projet");
        context.refresh();

        CategorieService categorieService = context.getBean(CategorieService.class);
        ProduitService produitService = context.getBean(ProduitService.class);
        CommandeService commandeService = context.getBean(CommandeService.class);
        LigneCommandeService ligneCommandeService = context.getBean(LigneCommandeService.class);

        /*CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneCommandeService = new LigneCommandeService();
*/
        Categorie cat1 = new Categorie("CAT1", "Catégorie 1", new ArrayList<>());
        categorieService.create(cat1);

        Produit p1 = new Produit("EG12", 120, cat1, new ArrayList<>());
        Produit p2 = new Produit("ZE65", 100, cat1, new ArrayList<>());
        Produit p3 = new Produit("EE85", 200, cat1, new ArrayList<>());

        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date commandeDate = null;
        try {
            commandeDate = sdf.parse("14/03/2013");
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        Commande cmd = new Commande(commandeDate, new ArrayList<>());
        commandeService.create(cmd);

        LigneCommandeProduit lcp1 = new LigneCommandeProduit(cmd, p1, 7);
        LigneCommandeProduit lcp2 = new LigneCommandeProduit(cmd, p2, 14);
        LigneCommandeProduit lcp3 = new LigneCommandeProduit(cmd, p3, 5);

        ligneCommandeService.create(lcp1);
        ligneCommandeService.create(lcp2);
        ligneCommandeService.create(lcp3);

        System.out.println("Commande : " + cmd.getId() + "    Date : " + sdf.format(cmd.getDate()));
        System.out.println("Liste des produits :");
        System.out.println("Référence    Prix    Quantité");

        List<LigneCommandeProduit> lignes = ligneCommandeService.getAll();
        for (LigneCommandeProduit ligne : lignes) {
            if (ligne.getCommande().getId() == cmd.getId()) {
                System.out.println(ligne.getProduit().getReference() + "     " + ligne.getProduit().getPrix() + "     " + ligne.getQuantite());
            }
        }

        produitService.getByCategorie("CAT1");
        System.out.println("Produits avec un prix supérieur à 100 :");

        List<Produit> produits = produitService.getProduitsPrixSuperieur();
        for (Produit produit : produits) {
            System.out.println("Référence: " + produit.getReference() + ", Prix: " + produit.getPrix());
        }

        ligneCommandeService.getProduitsCommande(1);
    }
}
