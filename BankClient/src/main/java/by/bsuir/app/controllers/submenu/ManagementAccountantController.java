package by.bsuir.app.controllers.submenu;

import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.CreditInfo;
import by.bsuir.app.entity.PassportData;
import by.bsuir.app.entity.enums.CreditTypes;
import by.bsuir.app.entity.enums.Role;
import by.bsuir.app.entity.enums.SecurityType;
import by.bsuir.app.exception.GettingDataException;
import by.bsuir.app.services.DateHandler;
import by.bsuir.app.services.GeneralFuncWindow;
import by.bsuir.app.services.reportFactory.ReportFactory;
import by.bsuir.app.services.reportFactory.ReportTypes;
import by.bsuir.app.util.Commands;
import by.bsuir.app.util.connection.Phone;
import by.bsuir.app.util.constants.Paths;
import com.itextpdf.text.DocumentException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.FormatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static by.bsuir.app.util.constants.Constants.*;

@Log4j2
public class ManagementAccountantController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Account> accountant_table;

    @FXML
    private TableColumn<Account, String> name_column;

    @FXML
    private TableColumn<Account, String> surname_column;

    @FXML
    private TableColumn<Account, String> thirdName_column;

    @FXML
    private TableColumn<Account, Integer> age_column;

    @FXML
    private TableColumn<Account, String> gender_column;

    @FXML
    private TableColumn<Account, String> pass_num_column;

    @FXML
    private TableColumn<Account, String> pass_id_column;

    @FXML
    private TableColumn<Account, Date> bith_column;

    @FXML
    private TableColumn<Account, Date> expirity_column;

    @FXML
    private TableColumn<Account, Date> issue_column;
    @FXML
    private TextField filterField;

    @FXML
    private Label warning_account_label;

    @FXML
    private ComboBox<String> extension_box;

    @FXML
    private Button create_report_button;

    @FXML
    private Button buttonDeleteCustomer;

    @FXML
    private TableView<CreditInfo> credits_table;

    @FXML
    private TableColumn<CreditInfo, Long> id_column_credit;

    @FXML
    private TableColumn<CreditInfo, String> name_column_credit;

    @FXML
    private TableColumn<CreditInfo, BigDecimal> rate_column_credit;

    @FXML
    private TableColumn<CreditInfo, Integer> term_column_credit;

    @FXML
    private TableColumn<CreditInfo, String> type_column_credit;

    @FXML
    private TableColumn<CreditInfo, String> security_column_credit;

    @FXML
    private TableColumn<CreditInfo, String> way_column_credit;

    @FXML
    private TableColumn<CreditInfo, String> url_column_credit;

    @FXML
    private Label warning_credit_label;

    @FXML
    private Button back_button;

    @FXML
    void handleClose(MouseEvent event) {
        warning_account_label.getScene().getWindow().hide();
    }

    @FXML
    void onMouseClickAddCredit(MouseEvent event) {
        GeneralFuncWindow.openNewScene(Paths.WindowAddCredit);
        updateCreditTable();
    }

    @FXML
    void BithDate(TableColumn.CellEditEvent<Account, Date> event) {
        try {
            Account account = (Account) accountant_table.getSelectionModel().getSelectedItem();
            String oldDate = String.valueOf(event.getNewValue());
            java.sql.Date newDateString = DateHandler.convertDateFormatInSqlDate(oldDate);

            account.getData().setDateOfBirth(newDateString);
            sendEditedData(account);
        } catch (ParseException e) {
            warning_account_label.setText(INCORRECT_DATE_FORMAT_MSG);
        } catch (IllegalArgumentException e) {
            warning_account_label.setText(INCORRECT_DATE_CHRONOLOGY_MSG);
            updateCustomerTable();
        }
        warning_account_label.setVisible(true);
    }

    @FXML
    void onEditCreditName(TableColumn.CellEditEvent<CreditInfo, String> event) {
        CreditInfo credit = (CreditInfo) credits_table.getSelectionModel().getSelectedItem();
        String newValue = event.getNewValue();

        credit.setName(newValue);
        sendEditedData(credit);
    }

    @FXML
    void onEditPassNum(TableColumn.CellEditEvent<CreditInfo, String> event) {
        CreditInfo credit = (CreditInfo) credits_table.getSelectionModel().getSelectedItem();
        String newValue = event.getNewValue();

        credit.setName(newValue);
        sendEditedData(credit);
    }

    @FXML
    void onEditPeriod(TableColumn.CellEditEvent<CreditInfo, Integer> event) {
        CreditInfo credit = (CreditInfo) credits_table.getSelectionModel().getSelectedItem();
        Integer newValue = event.getNewValue();

        credit.setTerm(newValue);
        sendEditedData(credit);
    }

    @FXML
    void onEditRate(TableColumn.CellEditEvent<CreditInfo, BigDecimal> event) {
        CreditInfo credit = (CreditInfo) credits_table.getSelectionModel().getSelectedItem();
        BigDecimal newValue = event.getNewValue();

        credit.setPercentRate(newValue);
        sendEditedData(credit);
    }

    @FXML
    void onEditSecurity(TableColumn.CellEditEvent<CreditInfo, String> event) {
        CreditInfo credit = (CreditInfo) credits_table.getSelectionModel().getSelectedItem();
        String newValue = event.getNewValue();

        for (SecurityType p : SecurityType.values()) {
            if (newValue.equals(p.getRus())) {
                credit.setSecurityType(p.getRus());
                sendEditedData(credit);
                break;
            } else {
                warning_credit_label.setText(EDITING_DATA_FAILURE);
                warning_credit_label.setVisible(true);
            }
        }
    }

    @FXML
    void onEditType(TableColumn.CellEditEvent<CreditInfo, String> event) {
        CreditInfo credit = (CreditInfo) credits_table.getSelectionModel().getSelectedItem();
        String newValue = event.getNewValue();

        for (CreditTypes p : CreditTypes.values()) {
            if (newValue.equals(p.getRus())) {
                credit.setLoanType(p.getRus());
                sendEditedData(credit);
                break;
            } else {
                warning_credit_label.setText(EDITING_DATA_FAILURE);
                warning_credit_label.setVisible(true);
            }
        }
    }

    @FXML
    void onEditURL(TableColumn.CellEditEvent<CreditInfo, String> event) {
        CreditInfo credit = (CreditInfo) credits_table.getSelectionModel().getSelectedItem();
        String newValue = event.getNewValue();

        credit.setIconUrl(newValue);
        sendEditedData(credit);
    }

    @FXML
    void onEditWay(TableColumn.CellEditEvent<CreditInfo, String> event) {
        CreditInfo credit = (CreditInfo) credits_table.getSelectionModel().getSelectedItem();
        String newValue = event.getNewValue();

        if (!newValue.equals("наличными") && !newValue.equals("безналичный")) {
            warning_credit_label.setText(EDITING_DATA_FAILURE);
            warning_credit_label.setVisible(true);
            return;
        }
        credit.setProvidingWay(newValue);
        sendEditedData(credit);
    }

    @FXML
    void onStartPassID(TableColumn.CellEditEvent<Account, String> event) {
        Account account = (Account) accountant_table.getSelectionModel().getSelectedItem();
        String newValue = event.getNewValue();

        account.getData().setIdenNumber(newValue);
        sendEditedData(account);
    }

    @FXML
    void onMouseClickDeleteCredit(MouseEvent event) {
        try {
            CreditInfo creditInfo = (CreditInfo) credits_table.getSelectionModel().getSelectedItem();
            if (creditInfo != null) {
                Phone.sendOrGetData(Commands.DELETE_CREDIT_BY_ID, creditInfo.getId());
                warning_credit_label.setText(DELETE_SUCCESS_MSG);
                updateCreditTable();
            } else {
                warning_credit_label.setText(ERROR_SELECT_FIELD_MSG);
            }
        } catch (IOException | ClassNotFoundException | GettingDataException e) {
            warning_credit_label.setText(DELETE_FAIL_MSG);
            log.error(e);
            e.printStackTrace();
        }
        warning_credit_label.setVisible(true);
    }

    @FXML
    void onMouseClickReloadCredit(MouseEvent event) {
        updateCreditTable();
    }

    @FXML
    void onMouseClickSaveCredits(MouseEvent event) {
        try {
            Phone.sendOrGetData(Commands.SAVE_CREDIT_DATA_LOCAL_STORAGE, new CreditInfo());
            warning_credit_label.setText(CREATED_RESTORE_POINT_MSG);
            back_button.setDisable(false);
            warning_credit_label.setVisible(true);
        } catch (IOException | ClassNotFoundException | GettingDataException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onMouseClickBackCredits(MouseEvent event) {
        try {
            if (back_button.isDisable())
                throw new GettingDataException();

            Phone.sendOrGetData(Commands.RESTORE_CREDIT_DATA_LOCAL_STORAGE, new CreditInfo());
            updateCreditTable();
            warning_credit_label.setText(LAST_RESTORE_POINT_MSG);
            warning_credit_label.setVisible(true);
        } catch (GettingDataException e) {
            warning_credit_label.setText("Сначала необходимо создать точку восстановления.");
        } catch (IOException | ClassNotFoundException e) {
            log.error(e);
        }
    }

    private List<Account> accounts;
    private List<CreditInfo> credits;

    @FXML
    void initialize() {
        updateCustomerTable();
        updateCreditTable();

        ObservableList<String> ol_reports = FXCollections.observableArrayList();
        for (ReportTypes b : ReportTypes.values())
            ol_reports.add(String.valueOf(b));
        extension_box.setItems(ol_reports);
        extension_box.getSelectionModel().select(0);

        create_report_button.setOnAction(event -> {
            createReport();
        });

        buttonDeleteCustomer.setOnAction(actionEvent -> deleteAccount());

    }

    private void deleteAccount() {
        try {
            Account account = (Account) accountant_table.getSelectionModel().getSelectedItem();
            if (account != null) {
                Phone.sendOrGetData(Commands.DELETE_USER_BY_ID, account.getId());
                warning_account_label.setText(DELETE_SUCCESS_MSG);
                updateCustomerTable();
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

    private void createReport() {
        ReportFactory reportFactory = ReportFactory.getInstance();

        try {
            ReportTypes type = ReportTypes.valueOf(extension_box.getValue());
            if (type == ReportTypes.PDF)
                reportFactory.getReport(ReportTypes.PDF).createReport();
            else if (type == ReportTypes.TXT)
                reportFactory.getReport(ReportTypes.TXT).createReport();
            else
                warning_account_label.setText(REPORT_CHOOSE_EXTENSION_MSG);

            warning_account_label.setText(REPORT_SUCCESS_MSG);

        } catch (IOException | GettingDataException | ClassNotFoundException e) {
            log.error(e);
            warning_account_label.setText(REPORT_FAILURE_MSG);
        } catch (DocumentException e) {
        }

        warning_account_label.setVisible(true);
    }

    private void bindDataInAccountTable() {
        age_column.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getData().getAge()));

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        expirity_column.setCellFactory(column -> {
            TableCell<Account, Date> cell = new TableCell<>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        expirity_column.setCellValueFactory(param -> {
            PassportData personalData = param.getValue().getData();
            if (personalData.getDateOfBirth() != null)
                return new SimpleObjectProperty<>(personalData.getDateOfExpirity());
            else return null;
        });
        expirity_column.setCellFactory(TextFieldTableCell.forTableColumn(new FormatStringConverter<>(format)));


        issue_column.setCellFactory(column -> {
            TableCell<Account, Date> cell = new TableCell<>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        issue_column.setCellValueFactory(param -> {
            PassportData personalData = param.getValue().getData();
            if (personalData.getDateOfBirth() != null)
                return new SimpleObjectProperty<>(personalData.getDateOfIssue());
            else return null;
        });
        issue_column.setCellFactory(TextFieldTableCell.forTableColumn(new FormatStringConverter<>(format)));

        bith_column.setCellFactory(column -> {
            TableCell<Account, Date> cell = new TableCell<>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        bith_column.setCellValueFactory(param -> {
            PassportData personalData = param.getValue().getData();
            if (personalData.getDateOfBirth() != null)
                return new SimpleObjectProperty<>(personalData.getDateOfBirth());
            else return null;
        });
        bith_column.setCellFactory(TextFieldTableCell.forTableColumn(new FormatStringConverter<>(format)));

        name_column.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getData().getName()));
        surname_column.setCellValueFactory(
                param -> new SimpleObjectProperty<>(param.getValue().getData().getSurname()));
        thirdName_column.setCellValueFactory(
                param -> new SimpleObjectProperty<>(param.getValue().getData().getThirdname()));

        gender_column.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getData().getGender()));

        pass_id_column.setCellValueFactory(
                param -> new SimpleObjectProperty<>(param.getValue().getData().getIdenNumber()));
        pass_num_column.setCellValueFactory(
                param -> new SimpleObjectProperty<>(param.getValue().getData().getPassNumber()));

    }

    private void fillAccountTableWithFilteredData() {

        try {
            ObservableList<Account> ol_accounts = FXCollections.observableArrayList();

            accounts = (List<Account>) Phone.sendOrGetData(Commands.GET_ALL_USERS, new Account());
            accounts = accounts.stream().filter(x -> x.getRole().equals(Role.USER.toString())).collect(Collectors.toList());

            ol_accounts.addAll(accounts);

            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Account> filteredData = new FilteredList<>(ol_accounts, b -> true);

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
                            } else if (data.getPassNumber() != null && data.getPassNumber().toLowerCase().contains(
                                    lowerCaseFilter)) {
                                return true;
                            } else if (data.getIdenNumber() != null && data.getIdenNumber().contains(
                                    lowerCaseFilter)) {
                                return true;
                            } else if (data.getGender() != null && data.getGender().contains(
                                    lowerCaseFilter)) {
                                return true;
                            } else if (data.getAge() != null && String.valueOf(
                                    data.getAge()).toLowerCase().contains(
                                    lowerCaseFilter)) {
                                return true;
                            } else if (data.getDateOfExpirity() != null && String.valueOf(
                                    data.getDateOfExpirity()).toLowerCase().contains(
                                    lowerCaseFilter)) {
                                return true;
                            } else if (data.getDateOfIssue() != null && String.valueOf(
                                    data.getDateOfExpirity()).toLowerCase().contains(
                                    lowerCaseFilter)) {
                                return true;
                            } else
                                return false; // Does not match.
                        }
                    }
                    return false;
                });
            });
//             3. Wrap the FilteredList in a SortedList.
            javafx.collections.transformation.SortedList<Account> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            // 	  Otherwise, sorting the TableView would have no effect.
            sortedData.comparatorProperty().bind(accountant_table.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            accountant_table.setItems(sortedData);
        } catch (IOException | ClassNotFoundException | GettingDataException e) {
            e.printStackTrace();
        }
    }

    private void updateCreditTable() {
        bindDataInCreditTable();
        fillCreditTableWithFilteredData();
    }

    void updateCustomerTable() {
        bindDataInAccountTable();
        fillAccountTableWithFilteredData();
    }

    private void bindDataInCreditTable() {
        id_column_credit.setCellValueFactory(new PropertyValueFactory<>("id"));

        name_column_credit.setCellValueFactory(new PropertyValueFactory<>("name"));
        name_column_credit.setCellFactory(TextFieldTableCell.forTableColumn());

        rate_column_credit.setCellValueFactory(new PropertyValueFactory<>("percentRate"));
        rate_column_credit.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));

        term_column_credit.setCellValueFactory(new PropertyValueFactory<>("term"));
        term_column_credit.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        type_column_credit.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        type_column_credit.setCellFactory(TextFieldTableCell.forTableColumn());

        security_column_credit.setCellValueFactory(new PropertyValueFactory<>("securityType"));
        security_column_credit.setCellFactory(TextFieldTableCell.forTableColumn());

        way_column_credit.setCellValueFactory(new PropertyValueFactory<>("providingWay"));
        way_column_credit.setCellFactory(TextFieldTableCell.forTableColumn());

        url_column_credit.setCellValueFactory(new PropertyValueFactory<>("iconUrl"));
        url_column_credit.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void fillCreditTableWithFilteredData() {

        try {
            ObservableList<CreditInfo> ol_credits = FXCollections.observableArrayList();

            credits = (List<CreditInfo>) Phone.sendOrGetData(Commands.GET_ALL_CREDITS_INFO, new Account());
            ol_credits.addAll(credits);

            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<CreditInfo> filteredData = new FilteredList<>(ol_credits, b -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(e -> {
                    // If filter text is empty, display all persons.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();
                    return true;
                });
            });

            // 3. Wrap the FilteredList in a SortedList.
            SortedList<CreditInfo> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            // 	  Otherwise, sorting the TableView would have no effect.
            sortedData.comparatorProperty().bind(credits_table.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            credits_table.setItems(sortedData);
        } catch (IOException | ClassNotFoundException | GettingDataException e) {
            e.printStackTrace();
        }
    }

    boolean sendEditedData(CreditInfo mark) {
        try {
            Phone.sendOrGetData(Commands.ADD_OR_UPDATE_CREDIT, mark);
            updateCreditTable();
            warning_credit_label.setText(EDITING_DATA_SUCCESS);
        } catch (IOException | ClassNotFoundException | GettingDataException e) {
            warning_credit_label.setText(EDITING_DATA_FAILURE);
            log.error(e);
            e.printStackTrace();
            return false;
        }
        warning_credit_label.setVisible(true);
        return true;
    }

    private boolean sendEditedData(Account account) {
        try {
            Phone.sendOrGetData(Commands.USER_ADD_OR_UPDATE, account);
            updateCustomerTable();
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
}
