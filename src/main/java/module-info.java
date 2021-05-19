module org.nikhiladiga {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires org.controlsfx.controls;

    opens org.nikhiladiga to javafx.fxml;
    opens org.nikhiladiga.controller to javafx.fxml;
    exports org.nikhiladiga;
}