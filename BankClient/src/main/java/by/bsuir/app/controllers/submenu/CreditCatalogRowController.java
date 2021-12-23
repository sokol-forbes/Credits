package by.bsuir.app.controllers.submenu;

import by.bsuir.app.entity.*;
import by.bsuir.app.entity.enums.ContractStates;
import by.bsuir.app.exception.GettingDataException;
import by.bsuir.app.util.Commands;
import by.bsuir.app.util.connection.Phone;
import by.bsuir.app.util.constants.LocalStorage;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class CreditCatalogRowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imageView;

    @FXML
    private Label credit_name;

    @FXML
    private Label credit_type;

    @FXML
    private Label credit_percent;

    @FXML
    private Label credit_term;

    @FXML
    private Label providing_way;

    @FXML
    private Label security;

    @FXML
    private Label id_label_hide;

    @FXML
    private Hyperlink take_credit_button;

    @FXML
    private Label war_label;


    @FXML
    void handleClose(MouseEvent event) {
        imageView.getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
        initializeRowsWithData();
    }

    private void initializeRowsWithData() {


        addInfoInRow();

        take_credit_button.setOnAction(actionEvent -> {
//            try {
//                int count = (int) Phone.sendOrGetData(Commands.GET_COUNT_OF_USER_CREDITS_BY_LOGIN,
//                         LocalStorage.getAccount().getLogin());
//                System.out.println("\n\n" + count);
//                war_label.setText("У вас уже есть 2 кредита.");
//            } catch (IOException | ClassNotFoundException | GettingDataException e) {
//                e.printStackTrace();
//            }
            try {
                Contract contract = new Contract();

                Account account = (Account) Phone.sendOrGetData(Commands.GET_USER_BY_LOGIN,
                        LocalStorage.getAccount().getLogin());
                Currency currency = (Currency) Phone.sendOrGetData(Commands.GET_CURRENCY_BY_ID, 1L);
                CreditInfo creditInfo = (CreditInfo) Phone.sendOrGetData(Commands.GET_CREDIT_BY_ID,
                        Long.valueOf(id_label_hide.getText()));

                contract.setAuthor(account);
                contract.setState(ContractStates.IN_PROCESSING.getState());
                contract.setCreditInfo(creditInfo);

                contract.setCurrency(currency);
                contract.setAmount(new BigDecimal("5000.00"));

            Phone.sendOrGetData(Commands.ADD_NEW_CONTRACT, contract);
            war_label.setText("На рассмотрении");


            } catch (IOException | ClassNotFoundException | GettingDataException e) {
                e.printStackTrace();
                war_label.setText("Ошибка");
            }
            war_label.setVisible(true);

        });

    }

    private void addInfoInRow() {
        CreditInfo credit = LocalStorage.getFirstCredit();

        id_label_hide.setText(String.valueOf(credit.getId()));
        credit_name.setText(credit.getName());
        credit_type.setText(credit.getLoanType());
        credit_percent.setText(String.valueOf(credit.getPercentRate()));
        credit_term.setText(String.valueOf(credit.getTerm()));
        providing_way.setText(String.valueOf(credit.getProvidingWay()));
        security.setText(credit.getSecurityType());

        String photoURL = credit.getIconUrl();
        if (photoURL != null)
            imageView.setImage(new Image(new File(photoURL).toURI().toString()));

    }
}
