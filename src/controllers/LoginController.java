package controllers;

import classes.Package;
import data.MySQLConnectionData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    Button btnLogin;
    @FXML
    TextField tfUsername;
    @FXML
    PasswordField pwPassword;
    @FXML
    Label lblError;

    Stage stage;

    @FXML
    void initialize() {

        btnLogin.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                if (login()){
                    try {
                        stage.setScene(mainScene());
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    lblError.setText("Username or Password is incorrect. Please try again.");
                }
            }
        });
    }

    private Scene mainScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../layout/sample.fxml"));
        return new Scene(root);
    }

    public void passCurrentStage(Stage currStage) {
        stage = currStage;
    }

    private boolean login (){
        try{
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            String query = "SELECT Password FROM users WHERE Username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, tfUsername.getText());

            ResultSet rs = stmt.executeQuery();
            String storedPass = "";
            String inputPass = pwPassword.getText();
            while (rs.next()) {
                storedPass = rs.getString("Password");
            }
            if (storedPass.equals(inputPass)) {
                return true;
            } else {
                return false;
            }
        } catch (
        SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
