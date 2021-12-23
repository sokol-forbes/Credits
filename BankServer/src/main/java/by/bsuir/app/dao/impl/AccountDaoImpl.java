package by.bsuir.app.dao.impl;

import by.bsuir.app.dao.AccountDao;
import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.HistoryLog;
import by.bsuir.app.entity.enums.Role;
import by.bsuir.app.util.constants.Status;
import by.bsuir.app.util.Services;
import by.bsuir.app.util.HibernateUtil;
import by.bsuir.app.util.JavaMailUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static by.bsuir.app.util.constants.ConstantsMSG.PASSWORD_RECOVERY_TOPIC_MSG;

@Log4j2
public class AccountDaoImpl implements AccountDao {
    private static Session session;

    @Override
    public List<Account> findAll() {
        List<Account> accounts = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            accounts = session.createQuery("SELECT a FROM Account a", Account.class).getResultList();
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Optional<Account> findById(Long id) {
        Optional<Account> bs = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            bs = Optional.of(session.load(Account.class, id));
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return bs;
    }

    @Override
    public boolean delete(Account account) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(account);
            session.getTransaction().commit();
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Account> account = findById(id);
        try {
            account.ifPresent(this::delete);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean saveOrUpdate(Account account) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(account);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Role auth(Account account) {
        Account foundAccount = Optional.of(findByLogin(account.getLogin())).orElse(new Account());

        if (foundAccount.isBlocked())
            return Role.UNDEFINED;

        if (account.getLogin().equals(foundAccount.getLogin()) && account.getPassword().equals(foundAccount.getPassword()))
            for (Role e : Role.values()) {
                if (e.toString().equals(foundAccount.getRole())) {
                    addEntranceLog(foundAccount);
                    return e;
                }
            }
        return Role.UNREGISTERED;
    }

    @Override
    @SuppressWarnings("depricated")
    public Account findByLogin(String login) {
        Optional<Account> account = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Account.class);
            account = Optional.ofNullable((Account) criteria.add(Restrictions.eq("login", login))
                    .uniqueResult());
            session.close();
        } catch (Throwable e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return new Account();
        }
        return account.orElse(new Account());
    }

    @Override
    public List<Account> findAllByCriteria(String field) {
        return null;
    }

    @Override
    public String resetPassword(Account accountFromUser) {
        Optional<Account> accountOptional = Optional.of(findByLogin(accountFromUser.getLogin()));
        if (accountOptional.isEmpty())
            return Status.ACCOUNT_NOT_EXISTS.toString();

        Account account = accountOptional.get();
        if (!account.getEmail().equals(accountFromUser.getEmail()))
            return Status.INCORRECT_EMAIL.toString();

        String newPass = Services.generateNewPassword(account.getLogin());

        try {
            JavaMailUtil.send(account.getEmail(), PASSWORD_RECOVERY_TOPIC_MSG, newPass);
            account.setPassword(newPass);
            saveOrUpdate(account);
        } catch (MessagingException e) {
            log.error(e);
            return Status.MAIL_SENDING_ERROR.toString();
        }
        return newPass;
    }

    @Override
    public boolean registration(Account account) {
        Account a = findByLogin(account.getLogin());
        if (a.getId() == null) {
            account.setRole(Role.USER.toString());
            saveOrUpdate(account);
            return true;
        }
        return false;
    }

    @Override
    public void addEntranceLog(Account user) {

        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        user.addLog(new HistoryLog(new Timestamp(System.currentTimeMillis())));

        session.update(user);
        session.getTransaction().commit();
        session.close();
    }



}