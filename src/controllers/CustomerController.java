package controllers;

import classes.Customer;
import classes.Package;
import data.MySQLConnectionData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerController {

    // Customers
    @FXML
    private ComboBox cmbCustomers;
    @FXML
    private TextField txtCustFirstName;
    @FXML
    private TextField txtCustLastName;
    @FXML
    private TextField txtCustAddress;
    @FXML
    private TextField txtCustCity;
    @FXML
    private TextField txtCustProv;
    @FXML
    private TextField txtCustPostal;
    @FXML
    private TextField txtCustCountry;
    @FXML
    private TextField txtCustHomePhone;
    @FXML
    private TextField txtCustBusPhone;
    @FXML
    private TextField txtCustEmail;
    @FXML
    private TextField txtAgentId;
    @FXML
    private Button btnCustomerDelete;
    @FXML
    private Button btnCustomerEdit;
    @FXML
    private Button btnCustomerAdd;

    @FXML
    void initialize() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            Statement stmt = conn.createStatement();

            // Display values for Customers tab
            ResultSet rsCustomers = stmt.executeQuery("SELECT * FROM Customers");
            ObservableList<Customer> customerList = FXCollections.observableArrayList();
            //ArrayList listOfCustomers = new ArrayList();
            while (rsCustomers.next()) {
                customerList.add(new Customer(rsCustomers.getInt(1), rsCustomers.getString(2), rsCustomers.getString(3),
                        rsCustomers.getString(4), rsCustomers.getString(5), rsCustomers.getString(6),
                        rsCustomers.getString(7),
                        rsCustomers.getString(8),
                        rsCustomers.getString(9), rsCustomers.getString(10), rsCustomers.getString(11),
                        rsCustomers.getInt(12)));
                //listOfCustomers.add(rsCustomers.getInt(1));
            }
            //ObservableList<Integer> cust = FXCollections.observableArrayList(listOfCustomers);
            //cmbCustomers.getItems().addAll(cust);
            cmbCustomers.setItems(customerList);

            cmbCustomers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
                @Override
                public void changed(ObservableValue<? extends Customer> observableValue, Customer o, Customer t1) {

                    txtCustFirstName.setText(t1.getCustFirstName());
                    txtCustLastName.setText(t1.getCustLastName());
                    txtCustAddress.setText(t1.getCustAddress());
                    txtCustCity.setText(t1.getCustCity());
                    txtCustProv.setText(t1.getCustProv());
                    txtCustPostal.setText(t1.getCustPostal());
                    txtCustCountry.setText(t1.getCustCountry());
                    txtCustHomePhone.setText(t1.getCustHomePhone());
                    txtCustBusPhone.setText(t1.getCustBusPhone());
                    txtCustEmail.setText(t1.getCustEmail());
                    txtAgentId.setText(String.valueOf(t1.getAgentId()));

                }
            });

            conn.close();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
