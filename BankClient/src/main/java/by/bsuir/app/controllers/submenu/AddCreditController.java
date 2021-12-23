package by.bsuir.app.controllers.submenu;

import by.bsuir.app.entity.CreditInfo;
import by.bsuir.app.entity.Currency;
import by.bsuir.app.entity.enums.CreditTypes;
import by.bsuir.app.entity.enums.SecurityType;
import by.bsuir.app.exception.GettingDataException;
import by.bsuir.app.util.Commands;
import by.bsuir.app.util.connection.Phone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static by.bsuir.app.util.constants.Constants.LOAD_ICONS_PATH;

public class AddCreditController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> typeBox;

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
    private Button offer_button;

    @FXML
    private Label war_label;

    @FXML
    void handleClose(MouseEvent event) {
        typeBox.getScene().getWindow().hide();
    }

    CreditInfo creditInfo = new CreditInfo();

    @FXML
    void onMouseClickAddPhoto(MouseEvent event) {
        final FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select Pictures");

        // Set Initial Directory
        fileChooser.setInitialDirectory(
                new File(LOAD_ICONS_PATH));

        // Add Extension Filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        new FileChooser.ExtensionFilter("JPG", "*.jpg");

        File file = fileChooser.showOpenDialog(new Stage());

        creditInfo.setIconUrl(file.getAbsolutePath());
    }

    List<Currency> currencies = null;

    @FXML
    void initialize() {
        draw();

        offer_button.setOnAction(event -> {

            try {

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
                creditInfo.setAuthor("bank");

                Phone.sendOrGetData(Commands.ADD_NEW_CREDIT, creditInfo);

                war_label.setText("Создано.");
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

    }
}
