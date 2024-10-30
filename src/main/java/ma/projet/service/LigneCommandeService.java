package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {
    @Override
    public boolean create(LigneCommandeProduit ligneCommande) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(ligneCommande);
            transaction.commit();
            return true;
        }
    }

    @Override
    public LigneCommandeProduit getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(LigneCommandeProduit.class, id);
        }
    }

    @Override
    public List<LigneCommandeProduit> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from LigneCommandeProduit", LigneCommandeProduit.class).list();
        }
    }
}
