package by.bsuir.app.controllers.submenu;

import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.enums.Gender;
import by.bsuir.app.exception.GettingDataException;
import by.bsuir.app.util.Commands;
import by.bsuir.app.util.connection.Phone;
import by.bsuir.app.util.constants.LocalStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static by.bsuir.app.util.constants.Constants.EDITING_DATA_FAILURE;
import static by.bsuir.app.util.constants.Constants.EDITING_DATA_SUCCESS_UPDATE;

@Log4j2
public class EditController {

    @FXML
    private TextField login_field;

    @FXML
    private TextField mail_field;

    @FXML
    private TextField name_field;

    @FXML
    private TextField surname_field;

    @FXML
    private TextField thirdname_field;

    @FXML
    private ComboBox<Integer> age_box;

    @FXML
    private ComboBox<String> gender_box;

    @FXML
    private TextField birth_date;

    @FXML
    private TextField passport_num;

    @FXML
    private TextField passport_id;

    @FXML
    private TextField issuer_date;

    @FXML
    private TextField expirity_date;

    @FXML
    private Button editButton;

    @FXML
    private Label war_label;

    @FXML
    void closeHandler(MouseEvent event) {
        war_label.getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
        fillFieldsWithData();

        editButton.setOnAction(actionEvent -> {
            try {

                updateData();

            } catch (IOException | ClassNotFoundException | GettingDataException e) {
                log.error(e.getMessage());
                war_label.setText(EDITING_DATA_FAILURE);
                e.printStackTrace();
            }
            war_label.setVisible(true);
        });
    }

    void updateData() throws IOException, ClassNotFoundException, GettingDataException {

        try {
        Account account = (Account) Phone.sendOrGetData(Commands.GET_USER_BY_LOGIN,
                LocalStorage.getAccount().getLogin());

            if (!login_field.getText().isEmpty())
                account.setLogin(login_field.getText());
            if (!mail_field.getText().isEmpty())
                account.setEmail(mail_field.getText());
            if (!name_field.getText().isEmpty())
                account.getData().setName(name_field.getText());
            if (!surname_field.getText().isEmpty())
                account.getData().setSurname(surname_field.getText());
            if (!thirdname_field.getText().isEmpty())
                account.getData().setThirdname(thirdname_field.getText());
            if (!passport_num.getText().isEmpty())
                account.getData().setPassNumber(passport_num.getText());
            if (!passport_id.getText().isEmpty())
                account.getData().setIdenNumber(passport_id.getText());
            if (birth_date.getText() != null) {
                Date date = dateConverter(birth_date.getText());
                account.getData().setDateOfBirth(date);
            }
            if (!issuer_date.getText().isEmpty()) {
                Date date = dateConverter(issuer_date.getText());
                account.getData().setDateOfIssue(date);
            }
            if (!expirity_date.getText().isEmpty()) {
                Date date = dateConverter(expirity_date.getText());
                account.getData().setDateOfExpirity(date);
            }

            Phone.sendOrGetData(Commands.USER_ADD_OR_UPDATE, account);
            war_label.setText(EDITING_DATA_SUCCESS_UPDATE);
        } catch (ParseException e) {
            war_label.setText("Неверный формат даты.");
        }
        war_label.setVisible(true);
    }

    void fillFieldsWithData() {
        ObservableList<String> genderBox = FXCollections.observableArrayList();
        for (Gender g : Gender.values())
            genderBox.add(g.getGender());
        gender_box.setItems(genderBox);

        ObservableList<Integer> ageBox = FXCollections.observableArrayList();
        for (int i = 18; i < 100; i++)
            ageBox.add(i);
        age_box.setItems(ageBox);
    }

    private Date dateConverter(String date) throws ParseException {
        java.util.Date convertedDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        return new Date(convertedDate.getTime());
    }
}
