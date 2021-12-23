package by.bsuir.app.dao;


import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.enums.Role;

import java.util.List;

public interface AccountDao extends BaseDao<Long, Account> {
    /**
     * Method that use to authorize user
     * @param account - parameter which contains login and password
     * @return the user role for generating next window
     */
    Role auth(Account account);

    /**
     * Method that use to find a user by login
     * @param login - parameter by which searching on db
     * @return the user wrappered by Optional
     */
    Account findByLogin(String login);

    /**
     * Method that use to find all requests on db by special field
     * @param field - parameter by which requests are searching on db
     * @return list of requests with a necessary field
     */
    List<Account> findAllByCriteria(String field);

    /**
     * Method that use to send a new password to user email
     * @param accountFromUser - parameter by which we get and check email and login
     * @return if good - newPassword, else - bad_status
     */
    String resetPassword(Account accountFromUser);

    /**
     * Method that use for registration of a new user
     * @param account - parameter with data
     * @return result of operation
     */
    boolean registration(Account account);

    void addEntranceLog(Account account);


}
