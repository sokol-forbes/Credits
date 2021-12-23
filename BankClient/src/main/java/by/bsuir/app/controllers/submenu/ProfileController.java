package by.bsuir.app.controllers.submenu;

import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.enums.Gender;
import by.bsuir.app.exception.GettingDataException;
import by.bsuir.app.services.GeneralFuncWindow;
import by.bsuir.app.util.Commands;
import by.bsuir.app.util.connection.Phone;
import by.bsuir.app.util.constants.LocalStorage;
import by.bsuir.app.util.constants.Paths;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import static by.bsuir.app.entity.enums.Gender.convertRusNameToEnum;

@Log4j2
public class ProfileController {

    @FXML
    private ImageView image_box;

    @FXML
    private Label login_label;

    @FXML
    private Label mail_label;

    @FXML
    private Label sex_label;

    @FXML
    private Label age_label;

    @FXML
    private Label name_label;

    @FXML
    private Label surname_label;

    @FXML
    private Label third_name_label;

    @FXML
    private Label role;

    @FXML
    private Label birth_label;

    @FXML
    private Label pass_num_label;

    @FXML
    private Label path_iden_label;

    @FXML
    private Label issue_date;

    @FXML
    private Label expire_date;

    @FXML
    void handleClose(MouseEvent event) {
        GeneralFuncWindow.closeApplication();
    }

    @FXML
    void initialize() {
        updateData();
    }

    void updateData() {
        try {
            Account responseAccount = (Account) Phone.sendOrGetData(Commands.GET_USER_BY_LOGIN,
                    LocalStorage.getAccount().getLogin());

            LocalStorage.setAccount(responseAccount);

            Account account = LocalStorage.getAccount();

            login_label.setText(account.getLogin());
            mail_label.setText(account.getEmail());
            role.setText(LocalStorage.getAccount().getRole());

            if (account.getData() != null) {

                if (account.getData().getGender() != null) {
                    Gender sex = convertRusNameToEnum(account.getData().getGender());

                    if (sex == Gender.MALE)
                        image_box.setImage(new Image(new File(Paths.MAN_AVATAR_PATH).toURI().toString()));
                    else if (sex == Gender.FEMALE)
                        image_box.setImage(new Image(new File(Paths.WOMAN_AVATAR_PATH).toURI().toString()));
                    else
                        image_box.setImage(new Image(new File(Paths.UNDEFINED_AVATAR_PATH).toURI().toString()));

                    sex_label.setText(sex.getGender());
                }
                String name = account.getData().getName();
                if (name != null) {
                    name_label.setText(name);
                }

                String surname = account.getData().getSurname();
                if (surname != null) {
                    surname_label.setText(surname);
                }

                String thirdName = account.getData().getThirdname();
                if (thirdName != null) {
                    third_name_label.setText(thirdName);
                }

                String phone = account.getData().getPassNumber();
                if (phone != null)
                    pass_num_label.setText(phone);

                String passNumber = account.getData().getPassNumber();
                if (passNumber != null)
                    pass_num_label.setText(passNumber);

                String idenNumber = account.getData().getIdenNumber();
                if (idenNumber != null)
                    path_iden_label.setText(idenNumber);

                if (account.getData().getAge() != null) {
                    int age = account.getData().getAge();

                    if (age != 0)
                        age_label.setText(String.valueOf(age));
                }
                Date birth = account.getData().getDateOfBirth();
                if (birth != null)
                    birth_label.setText(birth.toString());

                Date date = account.getData().getDateOfIssue();
                if (date != null)
                    issue_date.setText(date.toString());

                Date endDate = account.getData().getDateOfExpirity();
                if (endDate != null)
                    expire_date.setText(endDate.toString());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException | GettingDataException e) {
            e.printStackTrace();
        }
    }

    public void iconHandler(MouseEvent mouseEvent) {
        GeneralFuncWindow.openNewScene(Paths.WindowEdit);
        updateData();
    }
}

