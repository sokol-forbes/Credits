package by.bsuir.app.controllers.submenu;

import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.PassportData;
import by.bsuir.app.entity.enums.Gender;
import by.bsuir.app.entity.enums.Role;
import by.bsuir.app.exception.GettingDataException;
import by.bsuir.app.util.Commands;
import by.bsuir.app.util.connection.Phone;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;

import static by.bsuir.app.util.constants.Constants.*;

@Log4j2
public class ManagementController {

    @FXML
    private TableView<Account> account_table;

    @FXML
    private TableColumn<?, Long> id_column;

    @FXML
    private TableColumn<Account, String> surname_column;

    @FXML
    private TableColumn<Account, String> name_column;

    @FXML
    private TableColumn<?, String> login_column;

    @FXML
    private TableColumn<?, String> mail_column;

    @FXML
    private TableColumn<?, String> role_column;

    @FXML
    private TableColumn<Account, String> gender_column;

    @FXML
    private TableColumn<Account, Boolean> account_ban;

    @FXML
    private Label warning_account_label;

    @FXML
    private TextField filterField;

    @FXML
    void handleClose(MouseEvent event) {
        account_table.getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
        updateAccountTable();
    }

    void bindDataInAccountTable() {
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        login_column.setCellValueFactory(new PropertyValueFactory<>("login"));
        login_column.setCellFactory(TextFieldTableCell.forTableColumn());
        mail_column.setCellValueFactory(new PropertyValueFactory<>("email"));
        mail_column.setCellFactory(TextFieldTableCell.forTableColumn());

        name_column.setCellValueFactory(
                param -> new SimpleObjectProperty<>(param.getValue().getData().getName()));

        name_column.setCellFactory(TextFieldTableCell.forTableColumn());

        gender_column.setCellValueFactory(
                param -> new SimpleObjectProperty<>(param.getValue().getData().getGender()));
        gender_column.setCellFactory(TextFieldTableCell.forTableColumn());

        surname_column.setCellValueFactory(
                param -> new SimpleObjectProperty<>(param.getValue().getData().getSurname()));
        surname_column.setCellFactory(TextFieldTableCell.forTableColumn());

        role_column.setCellValueFactory(new PropertyValueFactory<>("role"));
        role_column.setCellFactory(TextFieldTableCell.forTableColumn());
        account_ban.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isBlocked()));
        account_ban.setCellFactory(tc -> new CheckBoxTableCell<>());
    }

    void fillAccountTableWithFilteredData() {

        try {
            ObservableList<Account> ol_accounts = FXCollections.observableArrayList();

            List<Account> accounts = (List<Account>) Phone.sendOrGetData(Commands.GET_ALL_USERS, new Account());
            ol_accounts.addAll(accounts);

            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Account> filteredData = new FilteredList<>(ol_accounts, b -> true);

            System.out.println(filteredData);
            // 2. Set the filter Predicate whenever the filter changes.
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(e -> {
                    // If filter text is empty, display all persons.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    PassportData data = e.getData();

                    if (e.getEmail() != null && e.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (e.getLogin().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (e.getRole().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        if (data != null) {
                            if (data.getName() != null && data.getName().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } else if (data.getSurname() != null && data.getSurname().toLowerCase().contains(
                                    lowerCaseFilter)) {
                                return true;
                            } else if (data.getThirdname() != null && data.getThirdname().toLowerCase().contains(
                                    lowerCaseFilter)) {
                                return true;
                            } else if (data.getGender() != null && data.getGender().toLowerCase().contains(
                                    lowerCaseFilter)) {
                                return true;
                            } else
                                return false; // Does not match.
                        }
                    }
                    return false;
                });
            });

            // 3. Wrap the FilteredList in a SortedList.
            SortedList<Account> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            // 	  Otherwise, sorting the TableView would have no effect.
            sortedData.comparatorProperty().bind(account_table.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            account_table.setItems(sortedData);
        } catch (IOException | ClassNotFoundException | GettingDataException e) {
            e.printStackTrace();
        }
    }

    void updateAccountTable() {
        bindDataInAccountTable();
        fillAccountTableWithFilteredData();
    }

    boolean sendEditedData(Account account) {
        try {
            Phone.sendOrGetData(Commands.USER_ADD_OR_UPDATE, account);
            updateAccountTable();
            warning_account_label.setText(EDITING_DATA_SUCCESS);
        } catch (IOException | ClassNotFoundException | GettingDataException e) {
            warning_account_label.setText(EDITING_DATA_FAILURE);
            log.error(e);
            e.printStackTrace();
            return false;
        }
        warning_account_label.setVisible(true);
        return true;
    }

    @FXML
    void onEditMail(TableColumn.CellEditEvent<Account, String> employeesForTableStringCellEditEvent) {
        Account account = (Account) account_table.getSelectionModel().getSelectedItem();
        String newValue = employeesForTableStringCellEditEvent.getNewValue();

        account.setEmail(newValue);
        sendEditedData(account);
    }

    @FXML
    void onEditLogin(TableColumn.CellEditEvent<Account, String> employeesForTableStringCellEditEvent) {
        Account account = (Account) account_table.getSelectionModel().getSelectedItem();
        String newValue = employeesForTableStringCellEditEvent.getNewValue();

        account.setLogin(newValue);
        sendEditedData(account);
    }

    @FXML
    void onMouseClickDeleteAccount(MouseEvent event) {
        try {
            Account account = (Account) account_table.getSelectionModel().getSelectedItem();
            if (account != null) {
                Phone.sendOrGetData(Commands.DELETE_USER_BY_ID, account.getId());
                warning_account_label.setText(DELETE_SUCCESS_MSG);
                updateAccountTable();
            } else {
                warning_account_label.setText(ERROR_SELECT_FIELD_MSG);
            }
        } catch (IOException | ClassNotFoundException | GettingDataException e) {
            warning_account_label.setText(DELETE_FAIL_MSG);
            log.error(e);
            e.printStackTrace();
        }
        warning_account_label.setVisible(true);
    }

    @FXML
    void onMouseClickResetAccount(MouseEvent event) {
        warning_account_label.setVisible(false);
        updateAccountTable();
    }

    @FXML
    void onMouseClickBlockAccount(MouseEvent event) {
        Account account = account_table.getSelectionModel().getSelectedItem();

        account.setBlocked(!account.isBlocked());
        sendEditedData(account);
    }

    @FXML
    void onEditAge(TableColumn.CellEditEvent<Account, Integer> employeesForTableStringCellEditEvent) {
        Account account = (Account) account_table.getSelectionModel().getSelectedItem();
        int newValue = employeesForTableStringCellEditEvent.getNewValue();

        account.getData().setAge(newValue);
        sendEditedData(account);
    }

    @FXML
    void onEditGender(TableColumn.CellEditEvent<Account, String> employeesForTableStringCellEditEvent) {
        Account account = (Account) account_table.getSelectionModel().getSelectedItem();
        String newValue = employeesForTableStringCellEditEvent.getNewValue();
        for (Gender p : Gender.values()) {
            if (newValue.equals(p.getGender())) {
                account.getData().setGender(p.getGender());
                sendEditedData(account);
                break;
            } else {
                warning_account_label.setText(EDITING_DATA_FAILURE);
                warning_account_label.setVisible(true);
            }
        }
    }

    @FXML
    void onEditName(TableColumn.CellEditEvent<Account, String> employeesForTableStringCellEditEvent) {
        Account account = (Account) account_table.getSelectionModel().getSelectedItem();
        String newValue = employeesForTableStringCellEditEvent.getNewValue();

        account.getData().setName(newValue);
        sendEditedData(account);
    }

    @FXML
    void onEditRole(TableColumn.CellEditEvent<Account, String> employeesForTableStringCellEditEvent) {
        Account account = (Account) account_table.getSelectionModel().getSelectedItem();
        String newValue = employeesForTableStringCellEditEvent.getNewValue();
        for (Role p : Role.values()) {
            if (newValue.equals(p.toString())) {
                account.setRole(p.toString());
                sendEditedData(account);
                break;
            } else {
                warning_account_label.setText(EDITING_DATA_FAILURE);
                warning_account_label.setVisible(true);
            }
        }
    }

    @FXML
    void onEditSurname(TableColumn.CellEditEvent<Account, String> employeesForTableStringCellEditEvent) {
        Account account = (Account) account_table.getSelectionModel().getSelectedItem();
        String newValue = employeesForTableStringCellEditEvent.getNewValue();

        account.getData().setSurname(newValue);
        sendEditedData(account);
    }
}


