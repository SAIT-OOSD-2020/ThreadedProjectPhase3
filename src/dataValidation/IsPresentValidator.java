package dataValidation;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class IsPresentValidator {
    public boolean IsPresentValidator(TextField tf, String textFieldName){
        if (tf.getText() == null || tf.getText() == "")
        {
            new Alert(Alert.AlertType.INFORMATION,
                    "Please enter a value for " + textFieldName, ButtonType.CLOSE).showAndWait();
            return false;
        }
        else return true;
    }
}
