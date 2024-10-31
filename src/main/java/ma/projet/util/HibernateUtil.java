package ma.projet.util;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    @Autowired
    public HibernateUtil(SessionFactory sessionFactory) {
        HibernateUtil.sessionFactory = sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
