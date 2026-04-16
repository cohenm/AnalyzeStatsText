module com.cohenm.analyzer {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.cohenm.analyzer.app;
    opens com.cohenm.analyzer.app to javafx.fxml;
    exports com.cohenm.analyzer.cli;
    opens com.cohenm.analyzer.cli to javafx.fxml;
    exports com.cohenm.analyzer.fx;
    opens com.cohenm.analyzer.fx to javafx.fxml;
}