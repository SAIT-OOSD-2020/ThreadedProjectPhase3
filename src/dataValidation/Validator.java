package dataValidation;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class Validator {
    public boolean IsPresentValidator(TextField tf, String textFieldName){
        if (tf.getText() == null || tf.getText().equals(""))
        {
            new Alert(Alert.AlertType.INFORMATION,
                    "Please enter a value for " + textFieldName, ButtonType.CLOSE).showAndWait();
            return false;
        }
        else return true;
    }

    public boolean IsNonNumericValidator(TextField tf, String textFieldName){
        if (tf.getText().matches("^([A-Za-z]|[0-9])+$"))
        {
            new Alert(Alert.AlertType.INFORMATION,
                    "Please do not include numeric values for " + textFieldName, ButtonType.CLOSE).showAndWait();
            return false;
        }
        else return true;
    }
}
