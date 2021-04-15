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
    Customer selectedCustomer = new Customer();


    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        tableViewCustomers.getSelectionModel().selectFirst();


//        TableColumn colCustomerId =new TableColumn<>(),colAgentId= null;
//        TableColumn<Customer,String> colCustFirstName= null,colCustLastName= null,colCustAddress= null,colCustCity= null,colCustProv= null,
//                colCustPostal= null,colCustCountry= null,colCustHomePhone= null,colCustBusPhone= null,colCustEmail= null;


        fillCustomerTable();

        editButton.disableProperty().setValue(true);
        btnViewBookings.disableProperty().setValue(true);


        tableViewCustomers.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (t1.intValue() >= 0) {
                    selectedIndex = t1.intValue();
                }

            }
        });

        tableViewCustomers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observableValue, Customer customer, Customer t1) {

                if (t1 != null) {
//                        System.out.println("customer : "+t1);

                    editButton.disableProperty().setValue(false);
                    btnViewBookings.disableProperty().setValue(false);
//                        selectedCustomer.setCustomerId(t1.getCustomerId());
//                        selectedCustomer.setCustFirstName(t1.getCustFirstName());
//                        selectedCustomer.setCustLastName(t1.getCustLastName());
//                        selectedCustomer.setCustAddress(t1.getCustAddress());
//                        selectedCustomer.setCustCity(t1.getCustCity());
//                        selectedCustomer.setCustProv(t1.getCustProv());
//                        selectedCustomer.setCustPostal(t1.getCustPostal());
//                        selectedCustomer.setCustCountry(t1.getCustCountry());
//                        selectedCustomer.setCustHomePhone(t1.getCustHomePhone());
//                        selectedCustomer.setCustBusPhone(t1.getCustBusPhone());
//                        selectedCustomer.setCustEmail(t1.getCustEmail());
//                        selectedCustomer.setAgentId(t1.getAgentId());

                    selectedCustomer = new Customer(t1.getCustomerId(), t1.getCustFirstName(), t1.getCustLastName(), t1.getCustAddress(),
                            t1.getCustCity(), t1.getCustProv(), t1.getCustPostal(), t1.getCustCountry(), t1.getCustHomePhone(), t1.getCustBusPhone(),
                            t1.getCustEmail(), t1.getAgentId());
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

            for (int i = 1; i <= colCount; i++) {
                temp = new TableColumn();
                temp.setText(rsmd.getColumnLabel(i));
                temp.setCellValueFactory(new PropertyValueFactory<>(rsmd.getColumnLabel(i)));
                tableViewCustomers.getColumns().add(temp);
            }

            tableViewCustomers.setItems(customerList);
//            tableViewCustomers.requestFocus();
//            tableViewCustomers.getSelectionModel().select(1);
            conn.close();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
