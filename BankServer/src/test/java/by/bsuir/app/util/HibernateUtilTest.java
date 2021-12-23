package by.bsuir.app.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class HibernateUtilTest {


    @Test
    public void testHibernateUtilWhenGettingSessionReturnSession() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.openSession();

        Assert.assertNotNull(session);
    }

}