package sample;

import classes.Customer;
import classes.Package;
import classes.Product;
import classes.Supplier;
import data.MySQLConnectionData;
import dataValidation.Validator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

public class Controller {

    // Packages
    @FXML
    private TableView<Package> tblPackages;


    @FXML
    private TableColumn<Package, String> colPkgName;

    @FXML
    private TableColumn<Package, Date> colPkgStartDate;

    @FXML
    private TableColumn<Package, Date> colPkgEndDate;

    @FXML
    private TableColumn<Package, String> colPkgDesc;

    @FXML
    private TableColumn<Package, Currency> colPkgBasePrice;

    @FXML
    private TableColumn<Package, Currency> colPkgAgencyCommission;


    @FXML
    private Button btnPackageAdd;

    @FXML
    private Button btnPackageEdit;

    @FXML
    private Button btnPackageSave;

    @FXML
    private Button btnPackageDelete;


    @FXML
    private ComboBox cmbPackages;
    //private ComboBox<Package>  cmbPackages;

    @FXML
    private TextField txtPkgStartDate;

    @FXML
    private TextField txtPkgEndDate;

    @FXML
    private TextField txtPkgDesc;

    @FXML
    private TextField txtPkgBasePrice;

    @FXML
    private TextField txtPkgAgencyCommission;

    // Products
    @FXML
    private TableView<Product> tblProducts;

    @FXML
    private TableColumn<Product, Integer> colProductId;

    @FXML
    private TableColumn<Product, String> colProdName;

    @FXML
    private ListView lstProducts;

    @FXML
    private ComboBox cmbProducts;

    @FXML
    private TextField txtProdName;

    @FXML
    private Button btnProductDelete;


    @FXML
    private Button btnProductEdit;

    @FXML
    private Button btnProductAdd;

    // Bookings

    @FXML
    private Button btnBookingDelete;


    @FXML
    private Button btnBookingEdit;

    @FXML
    private Button btnBookingAdd;

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
    private ListView lstSuppliers;

    @FXML
    private ComboBox cmbSuppliers;


    @FXML
    void initialize() {
        try {
            String url = "jdbc:mysql://localhost:3306/travelexperts";
            String username = "root";
            String password = "";
            MySQLConnectionData MySQL = new MySQLConnectionData(url, username, password);
            Connection conn = MySQL.getMySQLConnection();
            Statement stmt = conn.createStatement();



            // Display values for Packages tab
            ResultSet rsPackages = stmt.executeQuery("SELECT * FROM Packages");
            ObservableList<Package> packageList = FXCollections.observableArrayList();
            ArrayList listOfPackages = new ArrayList();
            while (rsPackages.next()) {
                packageList.add(new Package(rsPackages.getInt(1), rsPackages.getString(2), rsPackages.getDate(3),
                        rsPackages.getDate(4), rsPackages.getString(5), rsPackages.getDouble(6),
                        rsPackages.getDouble(7)));
                listOfPackages.add(rsPackages.getString(2));
            }
            //colPackageId.setCellValueFactory(new PropertyValueFactory<>("PackageId"));
            colPkgName.setCellValueFactory(new PropertyValueFactory<>("PkgName"));
            colPkgStartDate.setCellValueFactory(new PropertyValueFactory<>("PkgStartDate"));
            colPkgEndDate.setCellValueFactory(new PropertyValueFactory<>("PkgEndDate"));
            colPkgDesc.setCellValueFactory(new PropertyValueFactory<>("PkgDesc"));
            colPkgBasePrice.setCellValueFactory(new PropertyValueFactory<>("PkgBasePrice"));
            colPkgAgencyCommission.setCellValueFactory(new PropertyValueFactory<>("PkgAgencyCommission"));
            tblPackages.setItems(packageList);
            ObservableList<Integer> pkg = FXCollections.observableArrayList(listOfPackages);

            cmbPackages.setItems(packageList);

            cmbPackages.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Package>() {
                @Override
                public void changed(ObservableValue<? extends Package> observableValue, Package o, Package t1) {

                    txtPkgStartDate.setText(t1.getPkgStartDate().toString());
                    txtPkgEndDate.setText(t1.getPkgEndDate().toString());
                    txtPkgDesc.setText(t1.getPkgDesc());
                    txtPkgBasePrice.setText(String.valueOf(t1.getPkgBasePrice()));
                    txtPkgAgencyCommission.setText(String.valueOf(t1.getPkgAgencyCommission()));
                    btnPackageEdit.setDisable(false);
                    btnPackageSave.setDisable(true);
                }
            });

            // Display values for Products tab
            ResultSet rsProducts = stmt.executeQuery("SELECT * FROM Products");
            ArrayList listOfProducts = new ArrayList();
            while (rsProducts.next()) {
                listOfProducts.add(rsProducts.getString(2));
            }
            ObservableList<Integer> intList = FXCollections.observableArrayList(listOfProducts);
            cmbProducts.getItems().addAll(intList);
            lstProducts.setItems(intList);

            // Display values for Suppliers tab
            ResultSet rsSuppliers = stmt.executeQuery("SELECT * FROM Suppliers");
            ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
            ArrayList listOfSuppliers = new ArrayList();
            while (rsSuppliers.next()) {
                supplierList.add(new Supplier(rsSuppliers.getInt(1), rsSuppliers.getString(2)));
                listOfSuppliers.add(rsSuppliers.getString(2));
            }
            ObservableList<Integer> sup = FXCollections.observableArrayList(listOfSuppliers);
            cmbSuppliers.getItems().addAll(sup);
            lstSuppliers.setItems(sup);

            // Display values for Customers tab
            ResultSet rsCustomers = stmt.executeQuery("SELECT * FROM Customers");
            ObservableList<Customer> customerList = FXCollections.observableArrayList();
            //ArrayList listOfCustomers = new ArrayList();
            while (rsCustomers.next()) {
                customerList.add(new Customer(rsCustomers.getInt(1), rsCustomers.getString(2), rsCustomers.getString(3),
                        rsCustomers.getString(4), rsCustomers.getString(5),rsCustomers.getString(6),
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        btnPackageEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                txtPkgStartDate.setEditable(true);
                txtPkgEndDate.setEditable(true);
                txtPkgDesc.setEditable(true);
                txtPkgBasePrice.setEditable(true);
                txtPkgAgencyCommission.setEditable(true);
                btnPackageEdit.setDisable(true);
                btnPackageSave.setDisable(false);
                btnPackageAdd.setDisable(true);
                cmbPackages.setEditable(true);

            }
        });

        btnPackageSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnBookingAdd.setDisable(false);
                txtPkgStartDate.setEditable(false);
                txtPkgEndDate.setEditable(false);
                txtPkgDesc.setEditable(false);
                txtPkgBasePrice.setEditable(false);
                txtPkgAgencyCommission.setEditable(false);
                btnPackageEdit.setDisable(false);
                btnPackageSave.setDisable(true);
                btnPackageAdd.setDisable(false);
                btnPackageDelete.setDisable(false);
                cmbPackages.setEditable(false);

            }
        });


    }

    @FXML
    private void handlePackageUpdate(ActionEvent event) {
        try {
            String url = "jdbc:mysql://localhost:3306/travelexperts";
            String username = "root";
            String password = "";
            MySQLConnectionData MySQL = new MySQLConnectionData(url, username, password);
            Connection conn = MySQL.getMySQLConnection();
            String query = "UPDATE Packages set PkgName = ?, PkgStartDate = ?, PkgEndDate = ?, PkgDesc = ?, " +
                    "PkgBasePrice = ?,  PkgAgencyCommission = ? where " +
                    "PackageId = ?";

//            if (stmt.executeUpdate() > 0)
//            {
//                new Alert(Alert.AlertType.INFORMATION,
//                        "Product updated successfully", ButtonType.CLOSE).showAndWait();
//            }
//            else
//            {
//                new Alert(Alert.AlertType.WARNING,
//                        "Product update failed", ButtonType.CLOSE).showAndWait();
//            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void handleProductAdd(ActionEvent event) {
        try {
            String url = "jdbc:mysql://localhost:3306/travelexperts";
            String username = "root";
            String password = "";
            MySQLConnectionData MySQL = new MySQLConnectionData(url, username, password);
            Connection conn = MySQL.getMySQLConnection();
            String sql = "insert into products (ProdName) values (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            Validator valid = new Validator();
            boolean isvalid = valid.IsPresentValidator(txtProdName, "Product Name") && valid.IsNonNumericValidator(txtProdName, "Product Name");

            if (isvalid) {
                stmt.setString(1, txtProdName.getText());
                if (stmt.executeUpdate() > 0) {
                    new Alert(Alert.AlertType.INFORMATION,
                            "Product inserted successfully", ButtonType.CLOSE).showAndWait();
                } else {
                    new Alert(Alert.AlertType.WARNING,
                            "Product insert failed", ButtonType.CLOSE).showAndWait();
                }
                ResultSet rsProducts = stmt.executeQuery("SELECT * FROM Products");
                ArrayList listOfProducts = new ArrayList();
                while (rsProducts.next()) {
                    listOfProducts.add(rsProducts.getString(2));
                }
                ObservableList<Integer> intList = FXCollections.observableArrayList(listOfProducts);
                cmbProducts.getItems().addAll(intList);
                lstProducts.setItems(intList);
            }

            conn.close();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "An SQL Exception occurred: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

}