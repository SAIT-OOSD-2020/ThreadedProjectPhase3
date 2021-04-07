package dataValidation;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

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
        Pattern NONDIGIT = Pattern.compile("\\D");

        if (NONDIGIT.matcher(tf.getText()).find())
        {
            new Alert(Alert.AlertType.INFORMATION,
                    "Please do not include numeric values or special characters for " + textFieldName,
                    ButtonType.CLOSE).showAndWait();
            return false;
        }
        else return true;
    }

//    public boolean INumericValidator(TextField tf, String textFieldName){
//
//    }
}
