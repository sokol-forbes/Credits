package by.bsuir.app.service.impl;

import by.bsuir.app.dao.AccountDao;
import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.enums.Role;
import by.bsuir.app.service.UserService;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest extends TestCase {
    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private UserService userService = new UserServiceImpl(accountDao);

    private Account user;

    @Before
    public void initializationData() {
        user = new Account();
        user.setLogin("Borya");
    }

    @Test
    public void testUserAuthenticationWhenGivenLoginReturnTrue() {

        given(accountDao.auth(user)).willReturn(Role.USER);

        boolean result = userService.auth(user);

        Assert.assertTrue(result);
        verify(accountDao).auth(user);
    }

    @Test
    public void testUserAuthenticationWhenGivenWrongLoginReturnFalse() {
        given(accountDao.auth(user)).willReturn(null);

        boolean result = userService.auth(user);

        Assert.assertFalse(result);
        verify(accountDao).auth(user);
    }

    @Test
    public void testUserFindByLoginWhenGivenExistingLoginReturnUser() {
        given(accountDao.findByLogin("Borya")).willReturn(user);

        Account resultUser = userService.findByLogin("Borya");

        Assert.assertEquals(user, resultUser);
    }

    @Test
    public void testFindAllByCriteriaWhenGivenCriteriaReturnNotNullList() {
        List<Account> localUsers = new ArrayList<>();
        localUsers.add(new Account(1L, "Borya","Borya"));
        localUsers.add(new Account(2L, "Masha","Masha"));

        given(accountDao.findAllByCriteria(anyString())).willReturn(localUsers);

        List mockitoList = Mockito.mock(List.class);
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);

        mockitoList.addAll(localUsers);

        List<Account> users = userService.findAllByCriteria("name");

        Assert.assertEquals(localUsers, users);
    }

    @Test
    public void testResetPasswordWhenGivenUserReturnPositive() {
        given(accountDao.saveOrUpdate(user)).willReturn(true);

        boolean result = userService.resetPassword(user);
        Assert.assertTrue(result);
    }

    @Test
    public void testResetPasswordWhenGivenUserReturnNegative() {
        given(accountDao.saveOrUpdate(user)).willReturn(false);

        boolean result = userService.resetPassword(user);
        Assert.assertFalse(result);
    }

    @Test
    public void testRegistrationWhenRepeatLoginReturnFalse() {
        given(accountDao.findByLogin("Borya")).willReturn(new Account(1L, "Borya", "Borya"));
//        given(userDao.saveOrUpdate(any())).willReturn(true);

        boolean result = userService.registration(user);

        assertFalse(result);
    }

    @Test
    public void testRegistrationWhenLoginNotExistReturnTrue() {
        given(accountDao.findByLogin("Borya")).willReturn(new Account());
        given(accountDao.saveOrUpdate(any())).willReturn(true);

        boolean result = userService.registration(user);

        assertTrue(result);
    }

    @Test
    public void testAddEntranceLog() {
        userService.addEntranceLog(user);
        Mockito.verify(accountDao, Mockito.times(1)).saveOrUpdate(any(Account.class));
    }
}