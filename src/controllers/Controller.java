package controllers;

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
    @FXML
    void initialize() {
        PackagesController pc = new PackagesController();
        ProductsController prc = new ProductsController();
        Products_SuppliersController sc = new Products_SuppliersController();
        CustomerController cc = new CustomerController();
        //BookingViewController bc = new BookingViewController();
        BookingsController bc = new BookingsController();
    }
}