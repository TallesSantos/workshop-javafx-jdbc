package common.workshopjavafxjdbc;

import common.workshopjavafxjdbc.gui.util.Alerts;
import common.workshopjavafxjdbc.gui.util.DataChangeListener;
import common.workshopjavafxjdbc.gui.util.Utils;
import common.workshopjavafxjdbc.model.entities.Department;
import common.workshopjavafxjdbc.model.entities.Seller;
import common.workshopjavafxjdbc.model.exeptions.ValidationException;
import common.workshopjavafxjdbc.model.services.DepartmentService;
import common.workshopjavafxjdbc.model.services.SellerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormController implements Initializable {

    private Seller entity;

    private SellerService service;

    private DepartmentService departmentService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker txtBirthDate;

    @FXML
    private TextField txtBaseSalary;

    @FXML
    private ComboBox<Department> comboBoxDepartment;

    @FXML
    private Label labelErrorName;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorBirthDate;

    @FXML
    private Label labelErrorBaseSalary;


    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;


    private ObservableList<Department> obList;

    public void setSeller(Seller entity) {
        this.entity = entity;
    }

    public void setServices(SellerService sellerService, DepartmentService departmentService) {
        this.service = sellerService;
        this.departmentService = departmentService;
    }

    public void subcribeDateChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    @FXML
    private void onBtSaveAction(ActionEvent event) {
        System.out.println("onBtSaveAction");

        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListener();
            Utils.currentStage(event).close();
        } catch (ValidationException e) {
            setErrorMessages(e.getErrors());
        } catch (db.DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void notifyDataChangeListener() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private Seller getFormData() {
        Seller obj = new Seller();

        ValidationException exception = new ValidationException("Validation Error");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addError("name", "Field can't be empty");
        }
        obj.setName(txtName.getText());

        if (exception.getErrors().size() > 0) {
            throw exception;
        }
        return obj;
    }

    @FXML
    private void onBtCancelAction(ActionEvent event) {
        System.out.println("onBtCancelAction");
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        gui.util.Constraints.setTextFieldInteger(txtId);
        gui.util.Constraints.setTextFieldMaxLength(txtName, 70);
        gui.util.Constraints.setTextFieldMaxLength(txtEmail, 60);
        gui.util.Constraints.setTextFieldDouble(txtBaseSalary);
        gui.util.Constraints.formatDatePicker(txtBirthDate, "dd/MM/yyyy");
        initializeComboBoxDepartment();
    }

    public void updateFormData() {

        if (entity == null) {
            throw new IllegalStateException("entity was null");
        }

        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));

        if (entity.getBirthDate() != null) {
            txtBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }
        if(entity.getDepartment() == null){
            comboBoxDepartment.getSelectionModel().selectFirst();
        }else {
            comboBoxDepartment.setValue(entity.getDepartment());
        }
    }

    public void loadAssociateObjects(){
        List<Department> list = departmentService.findAll();
        obList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obList);
    }


    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();
        if (fields.contains("name")) {
            labelErrorName.setText(errors.get("name"));
        }
    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }
}
