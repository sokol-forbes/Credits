package by.bsuir.app.util;

import by.bsuir.app.entity.Message;
import by.bsuir.app.util.JavaMailUtil;

import javax.mail.MessagingException;
import java.util.Random;

public class Services {

    public static String generateNewPassword(String login) {
        Random rand = new Random();
        char nextChar;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 8; i++) {
            // lowercase characters go from 97 to 122
            nextChar = (char) (rand.nextInt(26) + 97);
            sb.append(nextChar);
            if ((i + 1) % 5 == 0 && i != 7) sb.append('-');
        }

        return sb.toString();
//        return Math.abs(rand.nextInt() * Integer.parseInt(String.valueOf(login.hashCode())));
    }

    public static boolean sendMessage(Message message) throws MessagingException {
        JavaMailUtil.send(message.getRecipient(), message.getTopic(), message.getMessage());
        return true;
    }
}
