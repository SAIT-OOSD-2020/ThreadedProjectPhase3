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

    private int nextId;

    public void NextId(int id) {
        nextId = id;
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

        btnDeleteCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                Alert alert = new Alert(Alert.AlertType.WARNING,"Are you Sure, you want to delete this Customer?",ButtonType.CANCEL,ButtonType.YES);
//                Optional<ButtonType> result =  alert.showAndWait();
//
//                if(result.get() == ButtonType.YES){
//                    try {
//                        MySQLConnectionData MySQL = new MySQLConnectionData();
//                        Connection conn = MySQL.getMySQLConnection();
//                        Statement stmt = conn.createStatement();
//
//                        String sql = "Delete FROM customers where CustomerId = "+cust.getCustomerId();
//                        stmt.executeUpdate(sql);
//
//                        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Customer has been deleted", ButtonType.OK);
//                        conn.close();
//
//                        alert2.show();
//                        Stage stage = (Stage) btnSaveCustomer.getScene().getWindow();
//                        stage.close();
//
//                    } catch (SQLException throwables) {
//                        throwables.printStackTrace();
//                    }
//                }
            }
        });

        btnSaveCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    MySQLConnectionData MySQL = new MySQLConnectionData();
                    Connection conn = MySQL.getMySQLConnection();
//                    String sql =
//                            "INSERT INTO Customers(`CustomerId`, `CustFirstName`, `CustLastName`, `CustAddress`, " +
//                                    "`CustCity`, `CustProv`,`CustPostal`, `CustCountry`, `CustHomePhone`, `CustBusPhone`, `CustEmail`, `AgentId`) VALUES\n" +
//                                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                    String sql =
                            "INSERT INTO Customers(CustomerId, CustFirstName, CustLastName, CustAddress, " +
                                    "CustCity, CustProv,CustPostal, CustCountry, CustHomePhone, CustBusPhone, CustEmail, AgentId) VALUES\n" +
                                    "("+nextId+","+ txtCustFirstName.getText()+","+ txtCustLastName.getText()+","+txtCustAddress.getText()+
                                    ","+txtCustCity.getText()+","+ txtCustProv.getText()+","+ txtCustPostal.getText()+","+ txtCustCountry.getText()+","
                                    + txtCustHomePhone.getText()+","+ txtCustBusPhone.getText()+","+ txtCustEmail.getText()+","+ Integer.parseInt(txtAgentId.getText())+")";

//                    String sql =
//                            "INSERT INTO Customers VALUES\n" +
//                                    "("+nextId+","+ txtCustFirstName.getText()+","+ txtCustLastName.getText()+","+txtCustAddress.getText()+
//                                    ","+txtCustCity.getText()+","+ txtCustProv.getText()+","+ txtCustPostal.getText()+","+ txtCustCountry.getText()+","
//                                    + txtCustHomePhone.getText()+","+ txtCustBusPhone.getText()+","+ txtCustEmail.getText()+","+ Integer.parseInt(txtAgentId.getText())+")";

                        System.out.println(sql);
                    Statement stmt = conn.createStatement();
                    //int UpdatedRows = stmt.executeUpdate(sql);
                    //Alert alert;

//                    PreparedStatement stmt = conn.prepareStatement(sql);
//                    stmt.setInt(1, nextId);
//                    stmt.setString(2, txtCustFirstName.getText());
//                    stmt.setString(3, txtCustLastName.getText());
//                    stmt.setString(4, txtCustAddress.getText());
//                    stmt.setString(5, txtCustCity.getText());
//                    stmt.setString(6, txtCustProv.getText());
//                    stmt.setString(7, txtCustPostal.getText());
//                    stmt.setString(8, txtCustCountry.getText());
//                    stmt.setString(9, txtCustHomePhone.getText());
//                    stmt.setString(10, txtCustBusPhone.getText());
//                    stmt.setString(11, txtCustEmail.getText());
//                    stmt.setInt(12, Integer.parseInt(txtAgentId.getText()));
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
