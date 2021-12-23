package by.bsuir.app.services.reportFactory;

import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.PassportData;
import by.bsuir.app.entity.enums.Gender;
import by.bsuir.app.exception.GettingDataException;
import by.bsuir.app.util.Commands;
import by.bsuir.app.util.connection.Phone;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static by.bsuir.app.util.constants.Constants.*;

@Log4j2
public class ReportPDF implements Report {
    @Override
    public void createReport() throws IOException, GettingDataException, ClassNotFoundException, DocumentException {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        chooser.getExtensionFilters().add(extFilter);
        File file = chooser.showSaveDialog(new Stage());

        List<Account> accounts = (List<Account>) Phone.sendOrGetData(Commands.GET_ALL_USERS, new Account());
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();
//        String author = LocalStorage.getAccount().getLogin();
        String author = "ME";
        document.addAuthor(AUTHOR_MSG + author);
        document.addTitle(REPORT_TITLE_MSG);
        document.add(new Paragraph(REPORT_ACCOUNT_DATA_MSG + "                                         " +
                "                             " +
                AUTHOR_MSG + author + "\n\n"));

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

                if (personalData.getGender() != null) {
                    gender = DATA_MISSED_MSG;
                    String genString = personalData.getGender();
                    for (Gender g : Gender.values()) {
                        if (genString.equals(g.getGender())) {
                            gender = g.toString();
                            break;
                        }
                    }
                }

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

            Paragraph p = new Paragraph(
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
            document.add(p);
        }
        document.close();
    }
}
