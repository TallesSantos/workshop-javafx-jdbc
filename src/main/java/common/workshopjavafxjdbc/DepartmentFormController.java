package common.workshopjavafxjdbc;

import common.workshopjavafxjdbc.gui.util.Alerts;
import common.workshopjavafxjdbc.gui.util.DataChangeListener;
import common.workshopjavafxjdbc.gui.util.Utils;
import common.workshopjavafxjdbc.model.entities.Department;
import common.workshopjavafxjdbc.model.exeptions.ValidationException;
import common.workshopjavafxjdbc.model.services.DepartmentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class DepartmentFormController implements Initializable {

    private Department entity;

    private DepartmentService service;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    public void setDepartment(Department entity) {
        this.entity = entity;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.service = departmentService;
    }

    public void subcribeDateChangeListener(DataChangeListener listener){
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
        }catch (ValidationException e){
            setErrorMessages(e.getErrors());
        }
        catch (db.DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void notifyDataChangeListener() {
        for(DataChangeListener listener: dataChangeListeners){
            listener.onDataChanged();
        }
    }

    private Department getFormData() {
        Department obj = new Department();

        ValidationException exception = new ValidationException("Validation Error");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if(txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addError("name", "Field can't be empty");
        }
        obj.setName(txtName.getText());

        if(exception.getErrors().size() > 0){
            throw  exception;
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
        gui.util.Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void updateFormData() {

        if (entity == null) {
            throw new IllegalStateException("entyti was null");
        }

        txtId.setText(String.valueOf(entity.getId()));
        txtId.setText(entity.getName());
    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();
        if(fields.contains("name")){
            labelErrorName.setText(errors.get("name"));
        }
    }
}
