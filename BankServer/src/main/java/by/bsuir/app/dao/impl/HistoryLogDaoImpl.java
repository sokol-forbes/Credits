package by.bsuir.app.dao.impl;

import by.bsuir.app.dao.HistoryLogDao;
import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.HistoryLog;
import by.bsuir.app.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class HistoryLogDaoImpl implements HistoryLogDao {
    private static Session session;
    private static final String FIND_ALL_GROUPED_BY_DATE = "SELECT count(i.entrance) as quantity, " +
            "DATE_FORMAT(i.entrance, '%d %M %Y') as" +
            " date FROM HistoryLog i GROUP BY date";

    @Override
    public List<HistoryLog> findUserLogHistoryByLogin(String userLogin) {
        List<HistoryLog> historyLogs = null;

        try {
            AccountDaoImpl accountDao = new AccountDaoImpl();
            Optional<Account> account = Optional.of(accountDao.findByLogin(userLogin));

            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            if (account.isPresent())
                historyLogs = account.get().getLogs();
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return historyLogs;
    }

    @Override
    public List<Object[]> findAllGropedByDate() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery(FIND_ALL_GROUPED_BY_DATE);
        List<Object[]> list = query.getResultList();

        session.close();
        return list;
    }

    @Override
    public List<HistoryLog> findAllUserLaunches() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<HistoryLog> list = session.createQuery("SELECT a FROM HistoryLog a", HistoryLog.class).getResultList();
        session.close();
        return list;
    }

    @Override
    public List<HistoryLog> findAll() {
        return null;
    }

    @Override
    public Optional<HistoryLog> findById(Long id) {
        Optional<HistoryLog> bs = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            bs = Optional.of(session.load(HistoryLog.class, id));
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return bs;
    }

    @Override
    public boolean delete(HistoryLog historyLog) {
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(HistoryLog historyLog) {
        return false;
    }
}
