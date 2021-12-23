package by.bsuir.app.controllers.submenu;

import by.bsuir.app.entity.Contract;
import by.bsuir.app.util.constants.LocalStorage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CreditHistoryRowController {

    @FXML
    private Label id_label;

    @FXML
    private Label name_field;

    @FXML
    private Label amount_field;

    @FXML
    private Label params_field;

    @FXML
    private Label request_date_field;

    @FXML
    private Label solution_field;

    @FXML
    private Label author_name;

    @FXML
    void initialize() {
        initializeBlockWithData();
    }

    private void initializeBlockWithData() {

        Contract contract = LocalStorage.getFirstContract();

        if (contract != null) {
            id_label.setText(contract.getId().toString());
            name_field.setText(contract.getCreditInfo().getName());
            amount_field.setText(contract.getAmount().toString() + contract.getCurrency().getSymbol());
            StringBuilder sb = new StringBuilder();
            sb.append("Тип: ").append(contract.getCreditInfo().getLoanType()).append(", ")
                    .append("Предоставление: ").append(contract.getCreditInfo().getProvidingWay()).append(", ")
                    .append("Ставка: ").append(contract.getCreditInfo().getPercentRate()).append("%, ")
                    .append("Срок: ").append(contract.getCreditInfo().getTerm()).append("мес., ")
                    .append("Обеспечение: ").append(contract.getCreditInfo().getSecurityType());

            params_field.setText(sb.toString());
            if (contract.getDateOfSigning() != null)
                request_date_field.setText(contract.getDateOfSigning().toString());
            solution_field.setText(contract.getState());

            if (contract.getResponsible() != null && contract.getResponsible().getLogin() != null)
                author_name.setText(contract.getResponsible().getLogin());
        }
    }
}


