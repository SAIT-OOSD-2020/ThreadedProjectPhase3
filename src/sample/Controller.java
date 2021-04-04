package sample;

import classes.Package;
import classes.Product;
import classes.Supplier;
import data.MySQLConnectionData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    @FXML
    private ComboBox cmbPackages;
    @FXML
    private ComboBox<Package> cmbPackages1;

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
    private Button btnProductDelete;

    @FXML
    private Button btnProductSave;

    @FXML
    private Button btnProductEdit;

    @FXML
    private Button btnProductAdd;

    // Bookings

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
            ResultSet rsProducts = stmt.executeQuery("SELECT * FROM Products");

            ObservableList<Product> productList = FXCollections.observableArrayList();
            ArrayList listOfProducts = new ArrayList();
            while (rsProducts.next()) {
                productList.add(new Product(rsProducts.getInt(1), rsProducts.getString(2)));
                listOfProducts.add(rsProducts.getString(2));
            }
            colProductId.setCellValueFactory(new PropertyValueFactory<>("ProductId"));
            colProdName.setCellValueFactory(new PropertyValueFactory<>("ProdName"));
            tblProducts.setItems(productList);
            ObservableList<Integer> intList = FXCollections.observableArrayList(listOfProducts);
            cmbProducts.getItems().addAll(intList);
            lstProducts.setItems(intList);


            ResultSet rsSuppliers = stmt.executeQuery("SELECT * FROM Suppliers");
            ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
            ArrayList listOfSuppliers = new ArrayList();
            while (rsSuppliers.next()) {
                supplierList.add(new Supplier(rsSuppliers.getInt(1), rsSuppliers .getString(2)));
                listOfSuppliers.add(rsSuppliers.getString(2));
            }
            ObservableList<Integer> sup = FXCollections.observableArrayList(listOfSuppliers);
            cmbSuppliers.getItems().addAll(sup);
            lstSuppliers.setItems(sup);

            ResultSet rsPackages = stmt.executeQuery("SELECT * FROM Packages");

            ObservableList<Package> packageList = FXCollections.observableArrayList();
            ArrayList listOfPackages = new ArrayList();
            while (rsPackages.next()) {
                packageList.add(new Package(rsPackages.getInt(1), rsPackages.getString(2), rsPackages.getDate(3),
                        rsPackages.getDate(4), rsPackages.getString(5), rsPackages.getDouble(6),
                        rsPackages.getDouble(7)));
                listOfPackages.add(rsPackages.getString(2));
            }
            colPackageId.setCellValueFactory(new PropertyValueFactory<>("PackageId"));
            colPkgName.setCellValueFactory(new PropertyValueFactory<>("PkgName"));
            colPkgStartDate.setCellValueFactory(new PropertyValueFactory<>("PkgStartDate"));
            colPkgEndDate.setCellValueFactory(new PropertyValueFactory<>("PkgEndDate"));
            colPkgDesc.setCellValueFactory(new PropertyValueFactory<>("PkgDesc"));
            colPkgBasePrice.setCellValueFactory(new PropertyValueFactory<>("PkgBasePrice"));
            colPkgAgencyCommission.setCellValueFactory(new PropertyValueFactory<>("PkgAgencyCommission"));
            tblPackages.setItems(packageList);
            ObservableList<Integer> pkg = FXCollections.observableArrayList(listOfPackages);
            cmbPackages.getItems().addAll(pkg);

            cmbPackages1.setItems(packageList);

            cmbPackages1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Package>() {
                @Override
                public void changed(ObservableValue<? extends Package> observableValue, Package o, Package t1) {

                    txtPkgStartDate.setText(t1.getPkgStartDate().toString());

                    txtPkgEndDate.setText(t1.getPkgEndDate().toString());
                    txtPkgDesc.setText(t1.getPkgDesc().toString());
                    txtPkgBasePrice.setText(String.valueOf(t1.getPkgBasePrice()));
                    txtPkgAgencyCommission.setText(String.valueOf(t1.getPkgAgencyCommission()));
                }
            });


            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}