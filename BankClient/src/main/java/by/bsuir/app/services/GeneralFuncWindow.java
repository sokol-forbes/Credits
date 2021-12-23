package by.bsuir.app.services;

import by.bsuir.app.util.Commands;
import by.bsuir.app.util.connection.Phone;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class GeneralFuncWindow {

    public static void openNewScene(String window) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GeneralFuncWindow.class.getResource(window));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();

            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.showAndWait();
    }

    public static void closeApplication() {
        try {
            Phone.send(Commands.CLOSE_CONNECTION.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        System.exit(0);
    }
}
