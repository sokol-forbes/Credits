package by.bsuir.app.controllers;

import by.bsuir.app.animation.Shake;
import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.enums.Role;
import by.bsuir.app.exception.EntranceException;
import by.bsuir.app.exception.GettingDataException;
import by.bsuir.app.exception.RoleRecognitionException;
import by.bsuir.app.services.GeneralFuncWindow;
import by.bsuir.app.util.Commands;
import by.bsuir.app.util.connection.Phone;
import by.bsuir.app.util.constants.Constants;
import by.bsuir.app.util.constants.LocalStorage;
import by.bsuir.app.util.constants.Paths;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

import static by.bsuir.app.services.GeneralFuncWindow.openNewScene;
import static by.bsuir.app.util.constants.Constants.*;

@Log4j2
public class SingInController {

    @FXML
    private Hyperlink singUpButton;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField login_field;

    @FXML
    private Label msg_label;

    @FXML
    private Hyperlink forgotPasswordButton;

    @FXML
    private Button singInButton;

    @FXML
    void initialize() {

        singInButton.setOnAction(actionEvent -> {
            msg_label.setVisible(false);
            String loginText = login_field.getText().trim();
            String passText = password_field.getText().trim();

            if (!loginText.equals("") && !passText.equals("")) {
                loginUser(loginText, passText);
            } else {
                msg_label.setText(Constants.FILL_FIELDS_MSG);
                msg_label.setVisible(true);
            }
        });

//        catalog_button.setOnAction(actionEvent -> {
//            msg_label.setVisible(false);
//            openNewScene(Paths.WindowCatalog);
//        });

        forgotPasswordButton.setOnAction(actionEvent -> {
            msg_label.setVisible(false);
            openNewScene(Paths.WindowForgotPassword);
        });
        singUpButton.setOnAction(actionEvent -> {
            msg_label.setVisible(false);
            openNewScene(Paths.WindowSignUp);
        });

    }


    public void handleClose(MouseEvent mouseEvent) {
        GeneralFuncWindow.closeApplication();
    }

    private void loginUser(String loginText, String passText) {
        msg_label.setVisible(false);

        try {
            Account account = new Account(loginText, passText);

            Role accountRole = (Role) Phone.sendOrGetData(Commands.AUTHORISATION, account);
            account.setRole(accountRole.toString());
            LocalStorage.setAccount(account);

            switch (accountRole) {
                case UNDEFINED -> throw new EntranceException(ACCOUNT_IS_BLOCKED_MSG);
                case ADMIN -> openNewScene(Paths.WindowAdminClient);
                case ACCOUNTANT -> openNewScene(Paths.WindowAccountantClient);
                case USER -> openNewScene(Paths.WindowSimpleClient);
                case UNREGISTERED -> throw new EntranceException(INCORRECT_LOGIN_OR_PASSWORD_MSG);
                default -> throw new RoleRecognitionException(AGES_PERCENTAGE_MSG);
            }

        } catch (IOException | ClassNotFoundException | EntranceException | RoleRecognitionException | GettingDataException e) {
            log.error(Constants.AUTH_FAIL + e.getMessage());
            msg_label.setText(e.getMessage());
            msg_label.setVisible(true);
            Shake loginAnim = new Shake(login_field);
            Shake passwordAnim = new Shake(password_field);
            loginAnim.playAnim();
            passwordAnim.playAnim();
        }
    }

}