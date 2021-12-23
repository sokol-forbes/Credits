package by.bsuir.app.controllers.others;

import by.bsuir.app.services.GeneralFuncWindow;
import by.bsuir.app.util.connection.Phone;
import by.bsuir.app.util.constants.Constants;
import by.bsuir.app.util.constants.Paths;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static by.bsuir.app.util.constants.Constants.CONNECTION_WAIT_TIME;

@Log4j2
public class LoadingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ProgressIndicator progress_bar;

    @FXML
    private Label warning_label;

    @FXML
    private Label warning_label2;

    @FXML
    private Button deny_button;

    @FXML
    void initialize() {

        new SplashScreen().start();

        deny_button.setOnAction(actionEvent -> {
            System.exit(0);
        });

    }
    class SplashScreen extends Thread {
        public void run() {

            int attemptsCounter = 0;
            int CURRENT_PORT =  Constants.PORT;

            for (int i = 0; i < Constants.ATTEMPTS_COUNT; i++) {
                for (int j = 0; j < Constants.ATTEMPTS_COUNT; j++) {
                    try {
                        new Phone(new Socket(Constants.IP_ADDRESS, CURRENT_PORT));
                        i = Constants.ATTEMPTS_COUNT;
                        break;
                    } catch (IOException e) {
                        attemptsCounter++;
                        try {
                            Thread.sleep(CONNECTION_WAIT_TIME);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        log.error(e.getMessage() + " ");
                        e.printStackTrace();
                    }
                }
                warning_label2.setVisible(true);
                deny_button.setVisible(true);
                //CURRENT_PORT++;
            }

            if (attemptsCounter < 9) {
                Platform.runLater(() -> {
                    deny_button.getScene().getWindow().hide();
                    GeneralFuncWindow.openNewScene(Paths.WindowSignIn);
//                GeneralFuncWindow.openNewScene(Paths.WindowManagementAccountant);
                });
            }
        }
    }

}
