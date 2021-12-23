package by.bsuir.app.dao.impl;

import by.bsuir.app.dao.CreditInfoDao;
import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.CreditInfo;
import by.bsuir.app.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class CreditDaoImpl implements CreditInfoDao {
    private static Session session;
    private static final String GET_ALL_CREDITS_GROUPED_BY_NAME = "SELECT name, count(*) as quantity FROM credit_info group by name";

    @Override
    public List<CreditInfo> findAll() {
        List<CreditInfo> credits = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            credits = session.createQuery("SELECT a FROM CreditInfo a", CreditInfo.class).getResultList();
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return credits;
    }

    @Override
    public Optional<CreditInfo> findById(Long id) {
        Optional<CreditInfo> bs = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            bs = Optional.of(session.load(CreditInfo.class, id));
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return bs;
    }

    @Override
    public boolean delete(CreditInfo creditInfo) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(creditInfo);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<CreditInfo> creditInfo = findById(id);
        try {
            creditInfo.ifPresent(this::delete);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean saveOrUpdate(CreditInfo creditInfo) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(creditInfo);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<Object[]> findAllGroupedByCreditName() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        SQLQuery query = session.createSQLQuery(GET_ALL_CREDITS_GROUPED_BY_NAME);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List list = query.list();

        session.close();
        return list;
    }

    @Override
    public boolean save(CreditInfo creditInfo) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(creditInfo);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean hqlTruncate() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.createSQLQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
        session.createSQLQuery("truncate table credit_info").executeUpdate();

        session.getTransaction().commit();
        session.close();

        return true;
    }
}
