module Module {
    exports exception;
    exports persistentie;
    exports cui;
    exports resources;
    exports gui;
    exports main;
    exports domein;

    requires java.desktop;
    requires java.sql;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    opens main to javafx.graphics,javafx.fxml;
    opens gui to javafx.graphics,javafx.fxml;
}