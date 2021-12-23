package by.bsuir.app.controllers.submenu;

import by.bsuir.app.entity.CreditInfo;
import by.bsuir.app.entity.enums.SecurityType;
import by.bsuir.app.exception.GettingDataException;
import by.bsuir.app.util.Commands;
import by.bsuir.app.util.connection.Phone;
import by.bsuir.app.util.constants.LocalStorage;
import by.bsuir.app.util.constants.Paths;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Log4j2
public class CreditCatalogController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button INSURANCE_filter;

    @FXML
    private Button PLEDGE_filter;

    @FXML
    private Button SURETY_filter;

    @FXML
    private Button allFilter;

    @FXML
    private Button FORFEIT_filter;


    @FXML
    void handleClose(MouseEvent event) {
        scrollPane.getScene().getWindow().hide();
    }

    private List<CreditInfo> creditsWithFilter = null;
    private List<CreditInfo> creditsAll = null;

    {
       getInfoFromServer();
    }
    @FXML
    void initialize() {

        draw();

        allFilter.setOnAction(actionEvent -> setFilter(SecurityType.ALL));

        INSURANCE_filter.setOnAction(actionEvent -> setFilter(SecurityType.INSURANCE));

        PLEDGE_filter.setOnAction(actionEvent -> setFilter(SecurityType.PLEDGE));

        SURETY_filter.setOnAction(actionEvent -> setFilter(SecurityType.SURETY));

        FORFEIT_filter.setOnAction(actionEvent -> setFilter(SecurityType.FORFEIT));

    }

    private void draw() {

        int size = creditsWithFilter.size();
        Node[] nodes = new Node[size];
        VBox vBox = new VBox();
        for (int i = 0; i < size; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource(Paths.WindowCatalogRow));
                vBox.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        scrollPane.setContent(vBox);
    }

    private void resetFilter() {
        creditsWithFilter = new ArrayList<>(creditsAll);
    }

    private void serFilteredProductInStorage() {
        LocalStorage.setCredits(creditsWithFilter);
    }

    private void setFilter(SecurityType type) {
        resetFilter();
        if (type != SecurityType.ALL)
            creditsWithFilter = creditsWithFilter.stream().filter(
                    c -> c.getSecurityType().equals(type.getRus())).collect(
                    Collectors.toList());
        serFilteredProductInStorage();
        draw();
    }

    @FXML
    private void onClickReload(MouseEvent event) {
        getInfoFromServer();
        draw();
    }

    private void getInfoFromServer() {
        try {
            List<CreditInfo> credits = (List<CreditInfo>) Phone.sendOrGetData(Commands.GET_ALL_CREDITS_INFO, new CreditInfo());
            credits = credits.stream().filter(x -> x.getAuthor().equals("bank")).collect(Collectors.toList());
            creditsAll = new ArrayList<>(credits);
            creditsWithFilter = new ArrayList<>(credits);
            LocalStorage.setCredits(credits);
        } catch (IOException | ClassNotFoundException | GettingDataException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
