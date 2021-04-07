package controllers;

import classes.Package;
import classes.Product;
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

public class ProductsController {

    // Controls
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

    @FXML
    void initialize() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            Statement stmt = conn.createStatement();


            // Display values for Products tab
            ResultSet rsProducts = stmt.executeQuery("SELECT * FROM Products");
            ArrayList listOfProducts = new ArrayList();
            while (rsProducts.next()) {
                listOfProducts.add(rsProducts.getString(2));
            }
            ObservableList<Integer> intList = FXCollections.observableArrayList(listOfProducts);
            cmbProducts.getItems().addAll(intList);
            lstProducts.setItems(intList);

            conn.close();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void handleProductAdd(ActionEvent event) {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            String sql = "insert into products (ProdName) values (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            Validator valid = new Validator();
            boolean isvalid = valid.IsPresentValidator(txtProdName, "Product Name");// && valid.IsNonNumericValidator(txtProdName, "Product Name");

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
