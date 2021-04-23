/**
 * Sample Skeleton for 'customers.fxml' Controller Class
 */

package controllers;

import classes.Customer;
import data.MySQLConnectionData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tableViewCustomers"
    private TableView<Customer> tableViewCustomers; // Value injected by FXMLLoader

    @FXML // fx:id="btnViewBookings"
    private Button btnViewBookings; // Value injected by FXMLLoader


    @FXML // fx:id="editButton"
    private Button editButton; // Value injected by FXMLLoader

    @FXML // fx:id="editButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="editButton"
    private Button deleteButton; // Value injected by FXMLLoader


    int selectedIndex;
    Customer selectedCustomer;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        fillCustomerTable();
        tableViewCustomers.getSelectionModel().selectFirst();

/*        tableViewCustomers.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (t1.intValue() >= 0) {
                    selectedIndex = t1.intValue();
                }
            }
        });*/

        tableViewCustomers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observableValue, Customer customer, Customer t1) {

                if (t1 != null) {
                    selectedCustomer = tableViewCustomers.getSelectionModel().getSelectedItem();
                }
            }
        });

        btnViewBookings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../layout/customerBookings.fxml"));
                    /*
                     * if "fx:controller" is not set in fxml
                     * fxmlLoader.setController(NewWindowController);
                     */
                    fxmlLoader.setController(new CustomerBookingsController(selectedCustomer.getCustomerId()));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setTitle("Customer Bookings");
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();


//                        stage.setOnHiding(new EventHandler<WindowEvent>() {
//                            @Override
//                            public void handle(WindowEvent windowEvent) {
//                                fillCustomerTable();
//                                tableViewCustomers.refresh();
//                            }
//                        });

                } catch (IOException e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
            }
        });

        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../layout/customersEdit.fxml"));
                    /*
                     * if "fx:controller" is not set in fxml
                     * fxmlLoader.setController(NewWindowController);
                     */
                    fxmlLoader.setController(new CustomerEditController(selectedCustomer));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setTitle("Edit Customer");
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();


                    stage.setOnHiding(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent windowEvent) {
                            fillCustomerTable();
                            tableViewCustomers.getSelectionModel().select(selectedIndex);

                        }
                    });

                } catch (IOException e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../layout/customersAdd.fxml"));

                    fxmlLoader.setController(new CustomerAddController());
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setTitle("Add Customer");
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();

                    stage.setOnHiding(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent windowEvent) {
                            fillCustomerTable();
                            tableViewCustomers.getSelectionModel().select(selectedIndex);
                        }
                    });
                } catch (IOException e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Customer cust = new Customer(tableViewCustomers.getSelectionModel().getSelectedIndex());
                Customer customer = tableViewCustomers.getSelectionModel().getSelectedItem();
                System.out.println(customer.getCustomerId());
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Are you sure, you want to delete customer " + customer.getCustFirstName() + " " + customer.getCustLastName() + "?",
                        ButtonType.CANCEL,ButtonType.YES);
                Optional<ButtonType> result =  alert.showAndWait();

                if(result.get() == ButtonType.YES){
                    try {
                        MySQLConnectionData MySQL = new MySQLConnectionData();
                        Connection conn = MySQL.getMySQLConnection();
                        Statement stmt = conn.createStatement();

                        String sql = "Delete FROM customers where CustomerId = "+customer.getCustomerId();
                        stmt.executeUpdate(sql);

                        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Customer has been deleted", ButtonType.OK);
                        conn.close();

                        alert2.show();

                        fillCustomerTable();
                        tableViewCustomers.getSelectionModel().select(selectedIndex);

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

    }

    private void fillCustomerTable() {
        try {
//            tableViewCustomers = new TableView<>();
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            Statement stmt = conn.createStatement();

            // Display values for Customers tab
            ResultSet rsCustomers = stmt.executeQuery("SELECT * FROM customers");
            ResultSetMetaData rsmd = rsCustomers.getMetaData();
            ObservableList<Customer> customerList = FXCollections.observableArrayList();
            ArrayList listOfCustomers = new ArrayList();
            while (rsCustomers.next()) {
                customerList.add(new Customer(rsCustomers.getInt(1), rsCustomers.getString(2), rsCustomers.getString(3),
                        rsCustomers.getString(4), rsCustomers.getString(5), rsCustomers.getString(6),
                        rsCustomers.getString(7), rsCustomers.getString(8), rsCustomers.getString(9),
                        rsCustomers.getString(10), rsCustomers.getString(11), rsCustomers.getInt(12)));
                listOfCustomers.add(rsCustomers.getString(2));
            }
            rsCustomers.next();

            int colCount = rsmd.getColumnCount();

            TableColumn temp;
            tableViewCustomers.getColumns().clear();

            for (int i = 2; i <= colCount; i++) {
                temp = new TableColumn();
                temp.setText(rsmd.getColumnLabel(i));
                temp.setCellValueFactory(new PropertyValueFactory<>(rsmd.getColumnLabel(i)));
                tableViewCustomers.getColumns().add(temp);
            }

            tableViewCustomers.setItems(customerList);
            conn.close();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
