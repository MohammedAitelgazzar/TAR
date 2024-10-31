package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategorieService implements IDao<Categorie> {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public boolean create(Categorie o) {
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
    public Categorie getById(int id) {
        Transaction tx = null;
        Categorie categorie = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            categorie = session.get(Categorie.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return categorie;
    }

    @Override
    public List<Categorie> getAll() {
        Transaction tx = null;
        List<Categorie> categories = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            categories = session.createQuery("from Categorie", Categorie.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return categories;
    }
}
