package by.bsuir.app.dao.impl;

import by.bsuir.app.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class BaseDaoImpl {
    private static Session session;
    private static final String CLEAR_TABLE = "DELETE FROM %s";


    public int hqlTruncate(String myTable){

        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.createSQLQuery("truncate table Mark").executeUpdate();

        session.getTransaction().commit();
        session.close();

        return 1;
    }
}
