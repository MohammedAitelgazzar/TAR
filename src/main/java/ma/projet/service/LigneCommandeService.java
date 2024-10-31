package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class LigneCommandeService implements IDao<LigneCommandeProduit> {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public boolean create(LigneCommandeProduit o) {
        Transaction tx = null;
        boolean status = false;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            status = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public LigneCommandeProduit getById(int id) {
        Transaction tx = null;
        LigneCommandeProduit ligne = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            ligne = session.get(LigneCommandeProduit.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return ligne;
    }

    @Override
    public List<LigneCommandeProduit> getAll() {
        Transaction tx = null;
        List<LigneCommandeProduit> lignes = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            lignes = session.createQuery("from LigneCommandeProduit", LigneCommandeProduit.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return lignes;
    }

    public void getProduitsCommande(int commandeId) {
        Transaction tx = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();

            List<LigneCommandeProduit> lignes = session.createQuery(
                            "FROM LigneCommandeProduit lcp WHERE lcp.commande.id = :commandeId",
                            LigneCommandeProduit.class)
                    .setParameter("commandeId", commandeId)
                    .list();

            if (!lignes.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                String date = dateFormat.format(lignes.get(0).getCommande().getDate());

                System.out.println("Commande : " + commandeId + "    Date : " + date);
                System.out.println("Liste des produits : ");
                System.out.println("Reference    Prix    Quantité");

                for (LigneCommandeProduit ligne : lignes) {
                    System.out.println(
                            ligne.getProduit().getReference() + "    " +
                                    ligne.getProduit().getPrix() + " DH    " +
                                    ligne.getQuantite()
                    );
                }
            } else {
                System.out.println("Aucun produit trouvé pour la commande n° " + commandeId);
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}

