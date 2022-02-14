module common.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens common.workshopjavafxjdbc to javafx.fxml;
    exports common.workshopjavafxjdbc;
    exports common.workshopjavafxjdbc.model.entities;
    opens common.workshopjavafxjdbc.model.entities to javafx.fxml;
    exports common.workshopjavafxjdbc.model.services;
    opens common.workshopjavafxjdbc.model.services to javafx.fxml;
    exports common.workshopjavafxjdbc.model.exeptions;
    opens common.workshopjavafxjdbc.model.exeptions to javafx.fxml;

}