package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit p) {
        Session session = null;
        Transaction tx = null;
        boolean status = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(p);
            tx.commit();
            status = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return status;
    }

    @Override
    public Produit getById(int id) {
        Session session = null;
        Transaction tx = null;
        Produit produit = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            produit = session.get(Produit.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return produit;
    }

    @Override
    public List<Produit> getAll() {
        Session session = null;
        Transaction tx = null;
        List<Produit> produits = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            produits = session.createQuery("from Produit", Produit.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return produits;
    }

    public void getByCategorie(String codeCategorie) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            String hql = "SELECT p FROM Produit p JOIN FETCH p.categorie c WHERE c.code = :code";
            List<Produit> produits = session.createQuery(hql, Produit.class)
                    .setParameter("code", codeCategorie)
                    .list();

            if (!produits.isEmpty()) {
                Categorie categorie = produits.get(0).getCategorie();
                System.out.println("Catégorie : " + categorie.getLibelle());
                System.out.println("Liste des produits :");

                for (Produit produit : produits) {
                    System.out.printf(produit.getReference(), produit.getPrix() + "DH");
                }
            } else {
                System.out.println("Aucun produit trouvé pour la catégorie : " + codeCategorie);
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    public List<Produit> getProduitsPrixSuperieur() {
        Session session = null;
        Transaction tx = null;
        List<Produit> produits = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            produits = session.createNamedQuery("findByPrixSup100", Produit.class).list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return produits;
    }
}
