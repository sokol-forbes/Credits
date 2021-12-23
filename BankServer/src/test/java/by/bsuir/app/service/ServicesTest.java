package by.bsuir.app.service;

import by.bsuir.app.util.Services;
import org.junit.Assert;
import org.junit.Test;

public class ServicesTest {


    @Test
    public void testServicesTestWhenGenerateNewPasswordShouldGenerateNotNull() {

        String newPassword = Services.generateNewPassword("Dima");
        Assert.assertNotNull(newPassword);
    }

    @Test
    public void testServicesTestWhenGenerateNewPasswordShouldGenerateStringExpectedLength() {

        String newPassword = Services.generateNewPassword("Dima");
        Assert.assertEquals(9, newPassword.length());
    }


}