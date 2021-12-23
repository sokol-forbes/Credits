module MyApp {
    requires java.naming;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.apache.logging.log4j;
    requires static lombok;
    requires java.sql;
    requires java.desktop;
    requires itextpdf;

    opens by.bsuir.app.controllers.submenu to javafx.fxml;
    opens by.bsuir.app.controllers.others to javafx.fxml;
    opens by.bsuir.app.controllers to javafx.fxml;

    opens by.bsuir.app;
    opens by.bsuir.app.util;
    opens by.bsuir.app.entity to javafx.base, javafx.fxml;
    opens by.bsuir.app.entity.enums to javafx.base, javafx.fxml;
}
