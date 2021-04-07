package controllers;

import classes.Package;
import classes.Supplier;
import data.MySQLConnectionData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SuppliersController {

    @FXML
    private ListView lstSuppliers;
    @FXML
    private ComboBox cmbSuppliers;

    @FXML
    void initialize() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            Statement stmt = conn.createStatement();

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

            conn.close();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
