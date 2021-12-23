package by.bsuir.app.dao.impl;

import by.bsuir.app.dao.CurrencyDao;
import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.CreditInfo;
import by.bsuir.app.entity.Currency;
import by.bsuir.app.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class CurrencyDaoImpl implements CurrencyDao {

    private static Session session;

    @Override
    public List<Currency> findAll() {
        List<Currency> currencies = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            currencies = session.createQuery("SELECT a FROM Currency a", Currency.class).getResultList();
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return currencies;
    }

    @Override
    public Optional<Currency> findById(Long id) {
        Optional<Currency> bs = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            bs = Optional.of(session.load(Currency.class, id));
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return bs;
    }

    @Override
    public boolean delete(Currency currency) {
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(Currency currency) {
        return false;
    }
}
