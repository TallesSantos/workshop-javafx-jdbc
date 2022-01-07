package common.workshopjavafxjdbc;

import common.workshopjavafxjdbc.gui.util.Alerts;
import common.workshopjavafxjdbc.gui.util.DataChangeListener;
import common.workshopjavafxjdbc.gui.util.Utils;
import common.workshopjavafxjdbc.model.entities.Seller;
import common.workshopjavafxjdbc.model.services.SellerService;
import common.workshopjavafxjdbc.model.services.SellerService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerListController implements Initializable, DataChangeListener {

    protected SellerService service;

    private ObservableList<Seller> obsList;

    @FXML
    private Button btNew;

    @FXML
    private TableView<Seller> tableViewSeller;

    @FXML
    private TableColumn<Seller, Integer> tableColumnId;

    @FXML
    private TableColumn<Seller, String> tableColumnName;

    @FXML
    private TableColumn<Seller, Seller> tableColumnEDIT;

    @FXML
    private TableColumn<Seller, Seller> tableColumnREMOVE;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }


    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = ((Stage) Main.getMainScene().getWindow());
        tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
    }

    public void setSellerService(SellerService service) {
        this.service = service;
    }

    @FXML
    private void onBtNewAction(ActionEvent event) {
        System.out.println("onBtNewAction");
        Stage parentStage = Utils.currentStage(event);

        Seller obj = new Seller();

        createDialogForm(obj, "SellerForm.fxml", parentStage);
    }

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        List<Seller> list = service.findAll();
        obsList = FXCollections.observableList(list);
        tableViewSeller.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }

    private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//            Pane pane = loader.load();
//
//            SellerFormController controller = loader.getController();
//            controller.setS(obj);
//            controller.setSellerService(new SellerService());
//            controller.subcribeDateChangeListener(this);
//            controller.updateFormData();
//
//            Stage dialogueStage = new Stage();
//            dialogueStage.setTitle("Enter Seller data");
//            dialogueStage.setScene(new Scene(pane));
//            dialogueStage.setResizable(false);
//            dialogueStage.initOwner(parentStage);
//            dialogueStage.initModality(Modality.WINDOW_MODAL);
//            dialogueStage.showAndWait();
//
//        } catch (IOException e) {
//            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
//        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, "SellerForm.fxml", Utils.currentStage(event)));
            }
        });
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("remove");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));

            }
        });
    }

    private void removeEntity(Seller obj) {

        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to Delete?");
        if (result.get() == ButtonType.OK) {
            if (service == null) {
                throw new IllegalStateException("Service was null");
            }
            try {
                service.remove(obj);
                updateTableView();
            }catch (db.DbIntegrityException e){
                Alerts.showAlert("Error removing object", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

}
