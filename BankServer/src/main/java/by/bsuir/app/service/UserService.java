package by.bsuir.app.service;

import by.bsuir.app.entity.Account;

import java.util.List;

public interface UserService {
    boolean auth(Account account);
    Account findByLogin(String login);

    List<Account> findAllByCriteria(String field);

    boolean resetPassword(Account accountFromUser);

    boolean registration(Account account);

    void addEntranceLog(Account account);
}
