module common.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens common.workshopjavafxjdbc to javafx.fxml;
    exports common.workshopjavafxjdbc;
}