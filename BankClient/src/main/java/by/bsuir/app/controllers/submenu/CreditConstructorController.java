package by.bsuir.app.controllers.submenu;

import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.Contract;
import by.bsuir.app.entity.CreditInfo;
import by.bsuir.app.entity.Currency;
import by.bsuir.app.entity.enums.ContractStates;
import by.bsuir.app.entity.enums.CreditTypes;
import by.bsuir.app.entity.enums.SecurityType;
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
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreditConstructorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> typeBox;

    @FXML
    private ComboBox<String> currencyBox;

    @FXML
    private ComboBox<Double> rateBox;

    @FXML
    private ComboBox<String> providingWayBox;

    @FXML
    private ComboBox<Integer> termBox;

    @FXML
    private ComboBox<String> securityBox;

    @FXML
    private ComboBox<String> nameBox;

    @FXML
    private ComboBox<String> amountBox;

    @FXML
    private Button offer_button;

    @FXML
    private Label war_label;

    @FXML
    void handleClose(MouseEvent event) {
        typeBox.getScene().getWindow().hide();
    }

    List<Currency> currencies = null;

    @FXML
    void initialize() {
        draw();

        offer_button.setOnAction(event -> {

            try {
                Contract contract = new Contract();
                CreditInfo creditInfo = new CreditInfo();

                if (nameBox.getValue() == null || typeBox.getValue() == null)
                    throw new IllegalArgumentException("Заполните все поля.");

                String creditName = nameBox.getValue();
                String creditType = typeBox.getValue();

                creditInfo.setName(creditName);
                creditInfo.setLoanType(creditType);

                if (rateBox.getValue() == null || termBox.getValue() == null)
                    throw new IllegalArgumentException("Заполните все поля.");

                creditInfo.setPercentRate(BigDecimal.valueOf(rateBox.getValue()));
                creditInfo.setTerm(termBox.getValue());
                creditInfo.setProvidingWay(providingWayBox.getValue());
                creditInfo.setSecurityType(securityBox.getValue());
                creditInfo.setAuthor(LocalStorage.getAccount().getLogin());

                String amountString = amountBox.getValue().toString();

                System.out.println(amountString);
                Double amount = Double.valueOf(amountString);


                if (amount < 100 || amount > 100000)
                    throw new IllegalArgumentException("Сумма от 100 до 100.000");

                contract.setAmount(BigDecimal.valueOf(amount));
                contract.setCreditInfo(creditInfo);
                Account account = (Account) Phone.sendOrGetData(Commands.GET_USER_BY_LOGIN, LocalStorage.getAccount().getLogin());

                contract.setAuthor(account);

                int currencyBoxIndex = currencyBox.getSelectionModel().getSelectedIndex();
                contract.setCurrency(currencies.get(currencyBoxIndex));
                contract.setState(ContractStates.IN_PROCESSING.getState());

                Phone.sendOrGetData(Commands.ADD_NEW_CONTRACT, contract);

                war_label.setText("Заявление отправлено на рассмотрение.");
                offer_button.setDisable(true);
            } catch (IllegalArgumentException e) {
                war_label.setText(e.getMessage());
            } catch (IOException | GettingDataException | ClassNotFoundException e) {
                war_label.setText("Произошла ошибка. Повторите позже.");
                e.printStackTrace();
            }
            war_label.setVisible(true);
        });
    }

    private void draw() {
        ObservableList<String> ol_types = FXCollections.observableArrayList();
        for (CreditTypes m : CreditTypes.values()) {
            ol_types.add(m.getRus());
        }
        typeBox.setItems(ol_types);

        try {
            currencies = (List<Currency>) Phone.sendOrGetData(Commands.GET_ALL_CURRENCIES, new Currency());

            ObservableList<String> ol_currencies = FXCollections.observableArrayList();
            for (Currency c: currencies) {
                ol_currencies.add(c.getGenericSymbol());
            }
            currencyBox.setItems(ol_currencies);
        } catch (IOException | ClassNotFoundException | GettingDataException e) {
            e.printStackTrace();
        }

        ObservableList<Integer> ol_term = FXCollections.observableArrayList();
        for (int i = 1; i < 36; i++) {
            ol_term.add(i);
        }
        termBox.setItems(ol_term);

        ObservableList<Double> ol_rate = FXCollections.observableArrayList();
        for (double i = 1.0; i < 36.0; i++) {
            ol_rate.add(i);
        }
        rateBox.setItems(ol_rate);

        ObservableList<String> ol_security = FXCollections.observableArrayList();
        for (SecurityType m : SecurityType.values()) {
            ol_security.add(m.getRus());
        }
        ol_security.remove(ol_security.size() - 1);
        securityBox.setItems(ol_security);

        ObservableList<String> ol_providingWayBox = FXCollections.observableArrayList();
        ol_providingWayBox.addAll("наличными", "безналичный");
        providingWayBox.setItems(ol_providingWayBox);

        ObservableList<String> ol_amount = FXCollections.observableArrayList();
        for (double i = 1000.00; i < 10000.00; i+=1000) {
            ol_amount.add(String.valueOf(i));
        }
        amountBox.setItems(ol_amount);
    }
}
