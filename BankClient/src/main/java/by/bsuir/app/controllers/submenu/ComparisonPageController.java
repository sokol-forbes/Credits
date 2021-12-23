package by.bsuir.app.controllers.submenu;

import by.bsuir.app.util.constants.LocalStorage;
import by.bsuir.app.util.constants.Paths;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class ComparisonPageController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label warning_label;

    @FXML
    private HBox pane;

    @FXML
    void handleClose(MouseEvent event) {
        scrollPane.getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
        draw();
    }

    private void draw() {

        int size = LocalStorage.getCreditsToCompare().size();
        Node[] nodes = new Node[size];
        HBox hBox = new HBox();
        for (int i = 0; i < size; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource(Paths.WindowComparisonRow));
                hBox.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        scrollPane.setContent(hBox);
    }
}
