module main {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens presentation to javafx.fxml;

    exports presentation;
}
