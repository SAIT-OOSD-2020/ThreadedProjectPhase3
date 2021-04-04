package sample;

import classes.Package;
import classes.Product;
import data.MySQLConnectionData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.Currency;
import java.util.Date;

public class Controller {
    @FXML
    private TableView<Package> tblPackages;

    @FXML
    private TableView<Product> tblProducts;

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
    private TableColumn<Product, Integer> colProductId;

    @FXML
    private TableColumn<Product, String> colProdName;

    @FXML
    private Button btnProductDelete1;

    @FXML
    private Button btnProductSave1;

    @FXML
    private Button btnProductEdit1;

    @FXML
    private Button btnPackageAdd;

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

        assert tblPackages != null : "fx:id=\"tblPackages\" was not injected: check your FXML file 'sample.fxml'.";
        assert colPackageId != null : "fx:id=\"colPackageId\" was not injected: check your FXML file 'sample.fxml'.";
        assert colPkgName != null : "fx:id=\"colPkgName\" was not injected: check your FXML file 'sample.fxml'.";
        assert colPkgStartDate != null : "fx:id=\"colPkgStartDate\" was not injected: check your FXML file 'sample.fxml'.";
        assert colPkgEndDate != null : "fx:id=\"colPkgEndDate\" was not injected: check your FXML file 'sample.fxml'.";
        assert colPkgDesc != null : "fx:id=\"colPkgDesc\" was not injected: check your FXML file 'sample.fxml'.";
        assert colPkgBasePrice != null : "fx:id=\"colPkgBasePrice\" was not injected: check your FXML file 'sample.fxml'.";
        assert colPkgAgencyCommission != null : "fx:id=\"colPkgAgencyCommission\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnProductDelete1 != null : "fx:id=\"btnProductDelete1\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnProductSave1 != null : "fx:id=\"btnProductSave1\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnProductEdit1 != null : "fx:id=\"btnProductEdit1\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnPackageAdd != null : "fx:id=\"btnPackageAdd\" was not injected: check your FXML file 'sample.fxml'.";
        assert tblProducts != null : "fx:id=\"tblProducts\" was not injected: check your FXML file 'sample.fxml'.";
        assert colProductId != null : "fx:id=\"colProductId\" was not injected: check your FXML file 'sample.fxml'.";
        assert colProdName != null : "fx:id=\"colProdName\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnProductDelete != null : "fx:id=\"btnProductDelete\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnProductSave != null : "fx:id=\"btnProductSave\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnProductEdit != null : "fx:id=\"btnProductEdit\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnProductAdd != null : "fx:id=\"btnProductAdd\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnBookingDelete != null : "fx:id=\"btnBookingDelete\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnBookingSave != null : "fx:id=\"btnBookingSave\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnBookingEdit != null : "fx:id=\"btnBookingEdit\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnBookingAdd != null : "fx:id=\"btnBookingAdd\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnCustomerDelete != null : "fx:id=\"btnCustomerDelete\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnCustomerSave != null : "fx:id=\"btnCustomerSave\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnCustomerEdit != null : "fx:id=\"btnCustomerEdit\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnCustomerAdd != null : "fx:id=\"btnCustomerAdd\" was not injected: check your FXML file 'sample.fxml'.";


        try {
            String url = "jdbc:mysql://localhost:3306/travelexperts";
            String username = "root";
            String password = "";
            MySQLConnectionData MySQL = new MySQLConnectionData(url, username, password);
            Connection conn = MySQL.getMySQLConnection();

            Statement stmt = conn.createStatement();
            ResultSet rsProducts = stmt.executeQuery("SELECT * FROM Products");
            ObservableList<Product> productList = FXCollections.observableArrayList();


            while (rsProducts.next()) {
                productList.add(new Product(rsProducts.getInt(1), rsProducts.getString(2)));
            }
            colProductId.setCellValueFactory(new PropertyValueFactory<>("ProductId"));
            colProdName.setCellValueFactory(new PropertyValueFactory<>("ProdName"));
            tblProducts.setItems(productList);

            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}