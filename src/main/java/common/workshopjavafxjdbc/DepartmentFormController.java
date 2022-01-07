package common.workshopjavafxjdbc;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {

    private Department entity;

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

    public void setDepartment(Department entity){
        this.entity = entity;
    }

    @FXML
    private void onBtSaveAction(){
        System.out.println("onBtSaveAction");
    }

    @FXML
    private void onBtCancelAction(){
        System.out.println("onBtCancelAction");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes(){
        gui.util.Constraints.setTextFieldInteger(txtId);
        gui.util.Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void updateFormData(){

        if(entity == null){
            throw new IllegalStateException("entyti was null");
        }

        txtId.setText(String.valueOf(entity.getId()));
        txtId.setText(entity.getName());
    }

}
