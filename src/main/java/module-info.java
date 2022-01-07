module common.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens common.workshopjavafxjdbc to javafx.fxml;
    exports common.workshopjavafxjdbc;

}