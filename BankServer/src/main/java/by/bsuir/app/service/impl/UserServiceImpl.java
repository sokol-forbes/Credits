package by.bsuir.app.service.impl;

import by.bsuir.app.dao.AccountDao;
import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.HistoryLog;
import by.bsuir.app.entity.enums.Role;
import by.bsuir.app.service.UserService;
import by.bsuir.app.util.Services;
import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Log4j2
public class UserServiceImpl implements UserService {
    private AccountDao accountDao;

    public UserServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public boolean auth(Account account) {
        Role role = accountDao.auth(account);

        return role != null;
    }

    @Override
    public Account findByLogin(String login) {
        Optional<Account> account = Optional.of(accountDao.findByLogin(login));
        return account.orElse(null);
    }

    @Override
    public List<Account> findAllByCriteria(String field) {
        return accountDao.findAllByCriteria(field);
    }

    @Override
    public boolean resetPassword(Account accountFromUser) {
        String newPassword = Services.generateNewPassword(accountFromUser.getLogin());
        accountFromUser.setPassword(newPassword);

        return accountDao.saveOrUpdate(accountFromUser);
    }

    @Override
    public boolean registration(Account account) {
        Account a = accountDao.findByLogin(account.getLogin());
        if (a.getId() == null) {
            account.setRole(Role.USER.toString());
            accountDao.saveOrUpdate(account);
            return true;
        }
        return false;
    }

    @Override
    public void addEntranceLog(Account account) {
        HistoryLog entranceHistory = new HistoryLog(new Timestamp(System.currentTimeMillis()));
        account.addLog(entranceHistory);
        accountDao.saveOrUpdate(account);
    }
}
