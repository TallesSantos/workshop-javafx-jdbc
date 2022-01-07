package common.workshopjavafxjdbc;

import common.workshopjavafxjdbc.gui.util.Alerts;
import common.workshopjavafxjdbc.gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable {

    private DepartmentService service;

    private ObservableList<Department> obsList;

    @FXML
    private Button btNew;

    @FXML
    private TableView<Department> tableViewDepartment;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }



    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = ((Stage) Main.getMainScene().getWindow());
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
    }

    public void setDepartmentService(DepartmentService service){
        this.service = service;
    }

    @FXML
    private void onBtNewAction(ActionEvent event){
        System.out.println("onBtNewAction");
    Stage parentStage = Utils.currentStage(event);
        creatingDialogForm("DepartmentForm.fxml", parentStage);
    }

    public void updateTableView() throws IllegalAccessException {
        if(service == null){
            throw new IllegalAccessException("Service was null");
        }
        List<Department> list = service.findAll();
        obsList = FXCollections.observableList(list);
        tableViewDepartment.setItems(obsList);
    }

    private void creatingDialogForm(String absoluteName, Stage parentStage){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();
            Stage dialogueStage = new Stage();
            dialogueStage.setTitle("Enter Department data");
            dialogueStage.setScene(new Scene(pane));
            dialogueStage.setResizable(false);
            dialogueStage.initOwner(parentStage);
            dialogueStage.initModality(Modality.WINDOW_MODAL);
            dialogueStage.showAndWait();

        }catch (IOException e){
        Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
