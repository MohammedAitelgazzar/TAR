package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class ProduitService implements IDao<Produit> {
    @Override
    public boolean create(Produit produit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(produit);
            transaction.commit();
            return true;
        }
    }

    @Override
    public Produit getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Produit.class, id);
        }
    }

    @Override
    public List<Produit> getByCategorie(String categorie) {
        return getAll().stream()
                .filter(produit -> produit.getCategorie().equals(categorie))
                .collect(Collectors.toList());
    }

    @Override
    public List<Produit> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Produit", Produit.class).list();
        }
    }
}
