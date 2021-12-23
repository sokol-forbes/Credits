package by.bsuir.app.controllers.submenu;

import by.bsuir.app.entity.HistoryLog;
import by.bsuir.app.exception.GettingDataException;
import by.bsuir.app.services.DateHandler;
import by.bsuir.app.services.GeneralFuncWindow;
import by.bsuir.app.util.Commands;
import by.bsuir.app.util.connection.Phone;
import by.bsuir.app.util.constants.LocalStorage;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import lombok.extern.log4j.Log4j2;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static by.bsuir.app.util.constants.Constants.*;
import static by.bsuir.app.util.constants.Paths.REPORT_PATH;

@Log4j2
@SuppressWarnings("unchecked")
public class ActivityChartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox hBox;

    @FXML
    private Label warning_label;

    @FXML
    private Label number_label;

    @FXML
    private AreaChart<Number, String> chart;

    @FXML
    private CategoryAxis y;

    @FXML
    private NumberAxis x;

    @FXML
    private Button reportButton;

    @FXML
    private Button openButton;

    @FXML
    void handleClose(MouseEvent event) {
        GeneralFuncWindow.closeApplication();
    }

    @FXML
    void initialize() {
        fillActivityChart();

        openButton.setOnAction(actionEvent -> {
            File file = new File(REPORT_PATH);
            Desktop desktop = Desktop.getDesktop();
            if (file.exists()) {
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        reportButton.setOnAction(actionEvent -> {
            try (FileWriter writer = new FileWriter(REPORT_PATH, false)) {

                List<HistoryLog> logs = (List<HistoryLog>) Phone.sendOrGetData(Commands.GET_ALL_USER_LAUNCHES,
                        LocalStorage.getAccount());

                writer.write(REPORT_CREATE_DATE_MSG + DateHandler.getCurrentTimeStampString());

                StringBuilder report = new StringBuilder();
                report.append(REPORT_PATTERN_HEAD_MSG);

                for (HistoryLog l : logs)
                    report.append("\t")
                            .append(l.getId())
                            .append("\t" + l.getAccount_id())
                            .append("\t" + l.getEntrance() + "\n");

                writer.write(String.valueOf(report));
                openButton.setDisable(false);
                writer.flush();
            } catch (IOException | ClassNotFoundException | GettingDataException ex) {
                log.error(ex.toString() + DELIMITER_MSG + ex.getMessage());
            }
        });
    }

    private void fillActivityChart() {
        try {
            List<Object[]> logs = (List<Object[]>) Phone.sendOrGetData(Commands.GET_LAUNCHES_COUNT_DATA,
                    LocalStorage.getAccount());

            XYChart.Series<Number, String> seriesActivity =
                    new XYChart.Series<>();
            seriesActivity.setName(LAUNCHES_MSG);

            int countOfUsers = 0;

            for (Object[] i : logs) {
                Long countAuthOfDay = (Long) i[0];
                String authDate = i[1].toString();

                seriesActivity.getData().add(new XYChart.Data(authDate, countAuthOfDay));
                countOfUsers += countAuthOfDay;
            }
            number_label.setText(String.valueOf(countOfUsers));

            chart.getData().add(seriesActivity);

        } catch (IOException | ClassNotFoundException | GettingDataException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
