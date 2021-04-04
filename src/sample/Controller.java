package sample;

import classes.Package;
import classes.Product;
import data.MySQLConnectionData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

public class Controller {

    // Packages
    @FXML
    private TableView<Package> tblPackages;

    @FXML
    private TableColumn<Package, Integer> colPackageId;

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

    // Products
    @FXML
    private TableView<Product> tblProducts;

    @FXML
    private TableColumn<Product, Integer> colProductId;

    @FXML
    private TableColumn<Product, String> colProdName;

    @FXML
    private ListView<Product> lstProducts;

    @FXML
    private ComboBox cmbProducts;

    @FXML
    private Button btnProductDelete;

    @FXML
    private Button btnProductSave;

    @FXML
    private Button btnProductEdit;

    @FXML
    private Button btnProductAdd;

    @FXML
    private Button btnBookingDelete;

    @FXML
    private Button btnBookingSave;

    @FXML
    private Button btnBookingEdit;

    @FXML
    private Button btnBookingAdd;

    @FXML
    private Button btnCustomerDelete;

    @FXML
    private Button btnCustomerSave;

    @FXML
    private Button btnCustomerEdit;

    @FXML
    private Button btnCustomerAdd;

    @FXML
    void initialize() {


        try {
            String url = "jdbc:mysql://localhost:3306/travelexperts";
            String username = "root";
            String password = "";
            MySQLConnectionData MySQL = new MySQLConnectionData(url, username, password);
            Connection conn = MySQL.getMySQLConnection();

            Statement stmt = conn.createStatement();
            ResultSet rsProducts = stmt.executeQuery("SELECT * FROM Products");
            ObservableList<Product> productList = FXCollections.observableArrayList();

            ArrayList list = new ArrayList();

            while (rsProducts.next()) {
                productList.add(new Product(rsProducts.getInt(1), rsProducts.getString(2)));
                list.add(rsProducts.getString(2));
            }
            colProductId.setCellValueFactory(new PropertyValueFactory<>("ProductId"));
            colProdName.setCellValueFactory(new PropertyValueFactory<>("ProdName"));
            tblProducts.setItems(productList);

            
            ObservableList<Integer> intList = FXCollections.observableArrayList(list);
            cmbProducts.getItems().addAll(intList);

            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}