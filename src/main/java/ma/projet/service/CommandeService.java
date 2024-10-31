package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommandeService implements IDao<Commande> {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public boolean create(Commande o) {
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
    public Commande getById(int id) {
        Transaction tx = null;
        Commande commande = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            commande = session.get(Commande.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return commande;
    }

    @Override
    public List<Commande> getAll() {
        Transaction tx = null;
        List<Commande> commandes = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            commandes = session.createQuery("from Commande", Commande.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return commandes;
    }

    public List<Commande> getCommandesBetweenDates(Date startDate, Date endDate) {
        Transaction tx = null;
        List<Commande> commandes = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            String hql = "FROM Commande c WHERE c.date BETWEEN :startDate AND :endDate";
            commandes = session.createQuery(hql, Commande.class)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return commandes;
    }

}
