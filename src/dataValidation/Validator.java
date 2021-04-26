package dataValidation;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class Validator {

    public boolean IsPresent(TextField tf, String textFieldName){
        if (tf.getText() == null || tf.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Please enter a value for " + textFieldName);
            alert.showAndWait();
            return false;
        }
        else return true;
    }

    public boolean IsPresentAndDate(DatePicker dtp, String textFieldName){
        if (dtp.getValue() == null || dtp.getEditor().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Please enter a value for " + textFieldName);
            alert.showAndWait();
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

    public boolean IsNumeric(TextField tf, String textFieldName){
        if (tf.getText() != null || !tf.getText().equals("")) {
            try {
                double d = Double.parseDouble(tf.getText());
            } catch (NumberFormatException nfe) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Please us a number for " + textFieldName,
                        ButtonType.CLOSE).showAndWait();
                return false;
            }
            return true;
        }
        else return true;
    }

}
