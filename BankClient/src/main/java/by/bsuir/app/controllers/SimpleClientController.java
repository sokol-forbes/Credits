package by.bsuir.app.controllers;

import by.bsuir.app.services.GeneralFuncWindow;
import by.bsuir.app.util.constants.Paths;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SimpleClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button profile_button;

    @FXML
    private Button catalog_button;

    @FXML
    private Button history_button;

    @FXML
    private Button comparison_button;

    @FXML
    private Button exit_button;

    @FXML
    private Pane pane;

    @FXML
    private Button builder_button;

    @FXML
    private Label social_label;

    @FXML
    void handleClose(MouseEvent event) {
        GeneralFuncWindow.closeApplication();
    }

    @FXML
    void initialize() {
        final Node[] node = {null};

        profile_button.setOnAction(actionEvent -> {
            try {
                    pane.getChildren().remove(node[0]);
                    node[0] = (Node) FXMLLoader.load(getClass().getResource(Paths.WindowProfile));
                    pane.getChildren().add(node[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        catalog_button.setOnAction(actionEvent -> {
            try {
                pane.getChildren().remove(node[0]);
                node[0] = (Node) FXMLLoader.load(getClass().getResource(Paths.WindowCatalog));
                pane.getChildren().add(node[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        history_button.setOnAction(actionEvent -> {
            try {
                pane.getChildren().remove(node[0]);
                node[0] = (Node) FXMLLoader.load(getClass().getResource(Paths.WindowCreditHistory));
                pane.getChildren().add(node[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        builder_button.setOnAction(actionEvent -> {
            try {
                pane.getChildren().remove(node[0]);
                node[0] = (Node) FXMLLoader.load(getClass().getResource(Paths.WindowCreditBuilder));
                pane.getChildren().add(node[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        exit_button.setOnAction(actionEvent -> {
            Stage stage = (Stage) exit_button.getScene().getWindow();
            stage.close();
        });
    }
}
