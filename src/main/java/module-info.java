module com.testgame.testgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.testgame.testgame to javafx.fxml;
    exports com.testgame.testgame;
    exports com.testgame.testgame.entities;
    opens com.testgame.testgame.entities to javafx.fxml;
}
