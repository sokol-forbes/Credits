package by.bsuir.app.services.reportFactory;

import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.PassportData;
import by.bsuir.app.exception.GettingDataException;
import by.bsuir.app.util.Commands;
import by.bsuir.app.util.connection.Phone;
import by.bsuir.app.util.constants.LocalStorage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static by.bsuir.app.util.constants.Constants.*;

public class ReportTXT implements Report{
    @Override
    public void createReport() throws IOException, GettingDataException, ClassNotFoundException {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        chooser.getExtensionFilters().add(extFilter);
        File file = chooser.showSaveDialog(new Stage());


        PrintWriter out = new PrintWriter(file);
        out.print("\t\t\t\t\t"+ REPORT_ACCOUNT_DATA_MSG);
        out.print("\n\n");
        out.print("\t\t\t\t\t\t\t\t" + AUTHOR_MSG + LocalStorage.getAccount().getLogin() +"\n\n\n");

        List<Account> accounts = (List<Account>) Phone.sendOrGetData(Commands.GET_ALL_USERS, new Account());

        for (int j = 0; j < accounts.size(); j++) {

            String login = accounts.get(j).getLogin();
            String email = accounts.get(j).getEmail();
            if (email == null)
                email = DATA_MISSED_MSG;
            String role = accounts.get(j).getRole();
            PassportData personalData = accounts.get(j).getData();

            String name = null;
            String surname = null;
            String thirdName = null;
            String passNumber = null;
            String gender = null;
            String bithDate = null;
            String passIssue = null;
            String passId = null;
            String age = null;

            String expirityDate = null;
            if (personalData != null) {
                name = DATA_MISSED_MSG;
                if (personalData.getName() != null)
                    name = personalData.getName();
                surname = DATA_MISSED_MSG;
                if (personalData.getSurname() != null)
                    surname = personalData.getSurname();
                thirdName = DATA_MISSED_MSG;
                if (personalData.getThirdname() != null)
                    thirdName = personalData.getThirdname();

                passNumber = DATA_MISSED_MSG;
                if (personalData.getPassNumber() != null)
                    passNumber = personalData.getPassNumber();

                passId = DATA_MISSED_MSG;
                if (personalData.getIdenNumber() != null)
                    passId = personalData.getIdenNumber();

                gender = DATA_MISSED_MSG;
                if ( personalData.getGender() != null)
                gender = personalData.getGender();

                bithDate = DATA_MISSED_MSG;
                if (personalData.getDateOfBirth() != null)
                    bithDate = personalData.getDateOfBirth().toString();

                passIssue = DATA_MISSED_MSG;
                if (personalData.getDateOfIssue() != null)
                    passIssue = personalData.getDateOfIssue().toString();

                expirityDate = DATA_MISSED_MSG;
                if (personalData.getDateOfExpirity() != null)
                    expirityDate = personalData.getDateOfExpirity().toString();
                age = DATA_MISSED_MSG;
                if (personalData.getAge() != null)
                    age = personalData.getAge().toString();
            }

           out.println(
                   "Name: " + name +
                           "\nSurname: " + surname +
                           "\nThird_Name: " + thirdName +
                           "\nGender: " + gender +
                           "\nAge: " + age +
                           "\nBirth date: " + bithDate +
                           "\nPassport number: " + passNumber +
                           "\nPassport ID: " + passId +
                           "\nPassport issue date: " + passIssue +
                           "\nPassport expirity date: " + expirityDate +
                           "\n\n\n");
        }
        out.close();
    }
}
