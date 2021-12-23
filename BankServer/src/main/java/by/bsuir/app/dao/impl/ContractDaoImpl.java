package by.bsuir.app.dao.impl;

import by.bsuir.app.dao.AccountDao;
import by.bsuir.app.dao.ContractDao;
import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.Contract;
import by.bsuir.app.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.*;

public class ContractDaoImpl implements ContractDao {
    private static Session session;
    private AccountDao accountDao = new AccountDaoImpl();

    @Override
    public List<Contract> findAll() {
        List<Contract> contracts = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            contracts = session.createQuery("SELECT a FROM Contract a", Contract.class).getResultList();
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return contracts;
    }

    @Override
    public Optional<Contract> findById(Long id) {
        Optional<Contract> bs = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            bs = Optional.of(session.load(Contract.class, id));
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return bs;
    }

    @Override
    public boolean delete(Contract contract) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
//            session.createSQLQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
            session.delete(contract);
            session.getTransaction().commit();
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(Contract contract) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(contract);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<Contract> findAllByUserId(Long id) {
        List<Contract> contracts = null;

        Optional<Account> account = accountDao.findById(id);

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria myCriteria = session.createCriteria(Contract.class);
            Criterion nameCriteria = Restrictions.eq("author", account.get());
            myCriteria.add(nameCriteria);
            contracts = myCriteria.list();

            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (contracts != null) {
            Set<Contract> set = new HashSet<>(contracts);
            return new ArrayList<>(set);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean save(Contract contract) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(contract);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public int findUserContractsCountByLogin(String login) {
//        Account account = accountDao.findByLogin(login);
//        List list = null;
//        try {
//            session = HibernateUtil.getSessionFactory().openSession();
//            Criteria myCriteria = session.createCriteria(Contract.class);
//            Criterion nameCriteria = Restrictions.eq("user_id", account.getId());
//            myCriteria.add(nameCriteria);
//            list = myCriteria.list();
//
//            session.close();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }

//        if (list == null)
        return 0;
//        else
//            return list.size();
    }

}
