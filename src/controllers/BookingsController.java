package controllers;

import classes.Package;
import data.MySQLConnectionData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookingsController {
    // Bookings

    @FXML
    private Button btnBookingDelete;
    @FXML
    private Button btnBookingEdit;
    @FXML
    private Button btnBookingAdd;

    @FXML
    void initialize() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            Statement stmt = conn.createStatement();

            conn.close();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
