package by.bsuir.app.controllers.submenu;

import by.bsuir.app.entity.MyDataTransfer;
import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.Contract;
import by.bsuir.app.entity.CreditInfo;
import by.bsuir.app.entity.enums.ContractStates;
import by.bsuir.app.entity.enums.Role;
import by.bsuir.app.exception.GettingDataException;
import by.bsuir.app.services.GeneralFuncWindow;
import by.bsuir.app.util.Commands;
import by.bsuir.app.util.connection.Phone;
import by.bsuir.app.util.constants.LocalStorage;
import by.bsuir.app.util.constants.Paths;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static by.bsuir.app.util.constants.Constants.INCORRECT_DATA_MSG;

@Log4j2
public class CreditHistoryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label headName;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox hBox;

    @FXML
    private Label warning_label;

    @FXML
    private TextField text_field;

    @FXML
    private Button remove_button;

    @FXML
    private Button approve_button;

    @FXML
    private Button deny_button;


    @FXML
    void handleClose(MouseEvent event) {
        GeneralFuncWindow.closeApplication();
    }

    List<Contract> contracts = null;

    @FXML
    void initialize() throws IOException {

        draw();

        approve_button.setOnAction(actionEvent -> {
            String text = text_field.getText();

            try {
                Long id = Long.valueOf(text);
                if (!isValidIndex(id))
                    throw new NumberFormatException();

                MyDataTransfer dto = new MyDataTransfer(id, LocalStorage.getAccount().getLogin());

                Phone.sendOrGetData(Commands.APPROVE_CONTRACT_BY_ID, dto);

                warning_label.setText("Одобрено.");

                draw();
            } catch (NumberFormatException e) {
                warning_label.setText("Неккоректные данные.");
            } catch (IOException | GettingDataException | ClassNotFoundException e) {
                System.out.println(e);
                warning_label.setText("Ошибка.");
            }
            warning_label.setVisible(true);

        });

        deny_button.setOnAction(actionEvent -> {
            String text = text_field.getText();

            try {
                Long id = Long.valueOf(text);

                if (!isValidIndex(id))
                    throw new NumberFormatException();

                MyDataTransfer dto = new MyDataTransfer(id, LocalStorage.getAccount().getLogin());
                Phone.sendOrGetData(Commands.DENY_CONTRACT_BY_ID, dto);

                warning_label.setText("Кредит отклонен.");

                draw();
            } catch (NumberFormatException e) {
                warning_label.setText("Неккоректные данные.");
            } catch (IOException | GettingDataException | ClassNotFoundException e) {
                warning_label.setText("Ошибка.");
            }
            warning_label.setVisible(true);
        });

        remove_button.setOnAction(actionEvent -> {
            warning_label.setVisible(false);

            String text = text_field.getText();
            try {
                Long id = Long.valueOf(text);

                Contract contract = (Contract) Phone.sendOrGetData(Commands.GET_CONTRACT_BY_ID, id);

                if (contract.getState().equals(ContractStates.IN_PROCESSING.getState())) {
                    Phone.sendOrGetData(Commands.REMOVE_CONTRACT, contract);
                    warning_label.setText("Контракт отменен.");
                } else {
                    warning_label.setText("Контракт уже одобрен/отклонен.");
                }

            } catch (NumberFormatException e) {
                warning_label.setText(INCORRECT_DATA_MSG);
            } catch (IOException | GettingDataException | ClassNotFoundException e) {
                warning_label.setText("Произошла ошибка.");
                e.printStackTrace();
            }
            warning_label.setVisible(true);
        });
    }

    private boolean isValidIndex(Long index) {
        return contracts.stream().anyMatch(x -> Objects.equals(x.getId(), index));
    }

    private void postConstruct() {
        if (LocalStorage.getAccount().getRole().equals(Role.ACCOUNTANT.toString())) {
            remove_button.setVisible(false);
            approve_button.setVisible(true);
            deny_button.setVisible(true);
            text_field.setVisible(true);

            headName.setText("Управление заявлениями на кредит");
        }
    }

    private void draw() {
        try {
            if (LocalStorage.getAccount().getRole().equals(Role.ACCOUNTANT.toString())) {
                postConstruct();
                contracts = (List<Contract>) Phone.sendOrGetData(Commands.GET_ALL_CONTRACTS, new CreditInfo());

            } else {
                Account account = (Account) Phone.sendOrGetData(Commands.GET_USER_BY_LOGIN,
                        LocalStorage.getAccount().getLogin());

                contracts = (List<Contract>) Phone.sendOrGetData(Commands.GET_CONTRACTS_BY_USER_ID, account.getId());
            }
        } catch (ClassNotFoundException | GettingDataException | IOException e) {
            e.printStackTrace();
        }
        LocalStorage.setContracts(new ArrayList<>(contracts));

        int size = contracts.size();
        Node[] nodes = new Node[size];
        VBox vBox = new VBox();
        for (int i = 0; i < size; i++) {
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource(Paths.WindowHistoryRow));
            } catch (IOException e) {
                e.printStackTrace();
            }
            vBox.getChildren().add(nodes[i]);
        }
        scrollPane.setContent(vBox);
    }
}
