package by.bsuir.app.controllers.submenu;

import by.bsuir.app.entity.CreditInfo;
import by.bsuir.app.util.constants.LocalStorage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class ComparisonRowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox here;

    @FXML
    private Label name_label;

    @FXML
    private Label category_label;

    @FXML
    private Label design_label;

    @FXML
    private Label package_label;

    @FXML
    private Label price_label;

    @FXML
    private Label service_label;

    @FXML
    private Label repair_label;

    @FXML
    private Label manufucturer_label;
    @FXML
    private ImageView imageView;

    @FXML
    void initialize() {

        addInfoInRow();
    }

    private void addInfoInRow() {
        CreditInfo credit = LocalStorage.getFirstCreditToCompare();

        String name = credit.getName();

        if (name != null)
            name_label.setText(name);

//        category_label.setText(credit.getCategory());
//
//
//
//        manufucturer_label.setText(credit.getOwner().getRus());
//        String photoURL = credit.getPhotoURL();
//        if (photoURL != null)
//            imageView.setImage(new Image(new File(photoURL).toURI().toString()));

    }

}
