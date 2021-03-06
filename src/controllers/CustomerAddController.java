/**
 * Sample Skeleton for 'customersEdit.fxml' Controller Class
 */

package controllers;

import classes.Customer;
import data.MySQLConnectionData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerAddController {

//    public CustomerAddController() {
//    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="textFieldsCustomers"
    private AnchorPane textFieldsCustomers; // Value injected by FXMLLoader

    @FXML // fx:id="txtCustFirstName"
    private TextField txtCustFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="txtCustLastName"
    private TextField txtCustLastName; // Value injected by FXMLLoader

    @FXML // fx:id="txtCustAddress"
    private TextField txtCustAddress; // Value injected by FXMLLoader

    @FXML // fx:id="txtCustCity"
    private TextField txtCustCity; // Value injected by FXMLLoader

    @FXML // fx:id="txtCustProv"
    private TextField txtCustProv; // Value injected by FXMLLoader

    @FXML // fx:id="txtCustPostal"
    private TextField txtCustPostal; // Value injected by FXMLLoader

    @FXML // fx:id="txtCustCountry"
    private TextField txtCustCountry; // Value injected by FXMLLoader

    @FXML // fx:id="txtCustHomePhone"
    private TextField txtCustHomePhone; // Value injected by FXMLLoader

    @FXML // fx:id="txtCustBusPhone"
    private TextField txtCustBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="txtCustEmail"
    private TextField txtCustEmail; // Value injected by FXMLLoader

    @FXML // fx:id="txtAgentId"
    private TextField txtAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancelCustomer"
    private Button btnCancelCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="btnSaveCustomer"
    private Button btnSaveCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDeleteCustomer"
    private Button btnDeleteCustomer; // Value injected by FXMLLoader

    @FXML
//    private ComboBox<?> cmbProv;
    private ComboBox cmbProv;

    private int nextId;

    public void NextId(int id) {
        nextId = id;
    }

    public CustomerAddController() {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        btnCancelCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) btnCancelCustomer.getScene().getWindow();
                stage.close();
            }
        });


        btnSaveCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    MySQLConnectionData MySQL = new MySQLConnectionData();
                    Connection conn = MySQL.getMySQLConnection();

                    String sql =
                            "INSERT INTO customers VALUES\n" +
                                    "("+nextId+",'"+ txtCustFirstName.getText()+"','"+ txtCustLastName.getText()+"','"+txtCustAddress.getText()+
                                    "','"+txtCustCity.getText()+"','"+ cmbProv.getValue()+"','"+ txtCustPostal.getText()+
                                    "','"+ txtCustCountry.getText()+"','"
                                    + txtCustHomePhone.getText()+"','"+ txtCustBusPhone.getText()+"','"+ txtCustEmail.getText()+"','"+ Integer.parseInt(txtAgentId.getText())+"')";

                    Statement stmt = conn.createStatement();
                    int UpdatedRows = stmt.executeUpdate(sql);
                    Alert alert;


                    if(UpdatedRows >0){
                        alert = new Alert(Alert.AlertType.CONFIRMATION, "Update Successful!", ButtonType.OK);

                    }
                    else{
                        alert = new Alert(Alert.AlertType.ERROR, "There was an Error while Updating", ButtonType.OK);
                    }

                    conn.close();

                    alert.show();
                    Stage stage = (Stage) btnSaveCustomer.getScene().getWindow();
                    stage.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        });

    }
}
