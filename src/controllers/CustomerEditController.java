/**
 * Sample Skeleton for 'customersEdit.fxml' Controller Class
 */

package controllers;

import classes.Customer;
import data.MySQLConnectionData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CustomerEditController {

    private Customer cust;
    public CustomerEditController(Customer cust) {
//        super();
        this.cust = cust;
    }

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

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert textFieldsCustomers != null : "fx:id=\"textFieldsCustomers\" was not injected: check your FXML file 'customersEdit.fxml'.";
        assert txtCustFirstName != null : "fx:id=\"txtCustFirstName\" was not injected: check your FXML file 'customersEdit.fxml'.";
        assert txtCustLastName != null : "fx:id=\"txtCustLastName\" was not injected: check your FXML file 'customersEdit.fxml'.";
        assert txtCustAddress != null : "fx:id=\"txtCustAddress\" was not injected: check your FXML file 'customersEdit.fxml'.";
        assert txtCustCity != null : "fx:id=\"txtCustCity\" was not injected: check your FXML file 'customersEdit.fxml'.";
        assert txtCustProv != null : "fx:id=\"txtCustProv\" was not injected: check your FXML file 'customersEdit.fxml'.";
        assert txtCustPostal != null : "fx:id=\"txtCustPostal\" was not injected: check your FXML file 'customersEdit.fxml'.";
        assert txtCustCountry != null : "fx:id=\"txtCustCountry\" was not injected: check your FXML file 'customersEdit.fxml'.";
        assert txtCustHomePhone != null : "fx:id=\"txtCustHomePhone\" was not injected: check your FXML file 'customersEdit.fxml'.";
        assert txtCustBusPhone != null : "fx:id=\"txtCustBusPhone\" was not injected: check your FXML file 'customersEdit.fxml'.";
        assert txtCustEmail != null : "fx:id=\"txtCustEmail\" was not injected: check your FXML file 'customersEdit.fxml'.";
        assert txtAgentId != null : "fx:id=\"txtAgentId\" was not injected: check your FXML file 'customersEdit.fxml'.";
        assert btnCancelCustomer != null : "fx:id=\"btnCancelCustomer\" was not injected: check your FXML file 'customersEdit.fxml'.";
        assert btnSaveCustomer != null : "fx:id=\"btnSaveCustomer\" was not injected: check your FXML file 'customersEdit.fxml'.";


        txtCustFirstName.setText(cust.getCustFirstName());
        txtCustLastName.setText(cust.getCustLastName());
        txtCustAddress.setText(cust.getCustAddress());
        txtCustCity.setText(cust.getCustCity());
        txtCustProv.setText(cust.getCustProv());
        txtCustPostal.setText(cust.getCustPostal());
        txtCustCountry.setText(cust.getCustCountry());
        txtCustHomePhone.setText(cust.getCustHomePhone());
        txtCustBusPhone.setText(cust.getCustBusPhone());
        txtCustEmail.setText(cust.getCustEmail());
        txtAgentId.setText(cust.getAgentId()+"");

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
                    String sql = "Update customers Set CustFirstName = '"+txtCustFirstName.getText()+"', CustLastName= '"+txtCustLastName.getText()+"', "+
                            "CustAddress = '"+txtCustAddress.getText()+"', CustCity = '"+txtCustCity.getText()+"', CustProv = '"+txtCustProv.getText()+"', CustPostal = '"+ txtCustPostal.getText()+"', "+
                    "CustCountry = '"+txtCustCountry.getText()+"', CustHomePhone = '"+ txtCustHomePhone.getText()+"', CustBusPhone = '"+txtCustBusPhone.getText()+"', CustEmail = '"+txtCustEmail.getText()+"', "+
                    "AgentId = '"+ txtAgentId.getText()+"'";

                    sql+=" where CustomerId = "+cust.getCustomerId()+"";
                    System.out.println(sql);

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
