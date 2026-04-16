module com.cohenm.analyzer {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.cohenm.analyzer.app;
    opens com.cohenm.analyzer.app to javafx.fxml;
}