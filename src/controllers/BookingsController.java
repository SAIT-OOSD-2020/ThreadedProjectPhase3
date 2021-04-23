package controllers;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import classes.Booking;
import classes.BookingDetail;
import classes.Package;
import data.MySQLConnectionData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class BookingsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<ObservableList> tblBookings;

    @FXML
    void initialize() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            Statement stmt = conn.createStatement();

            ResultSet rsBookings = stmt.executeQuery("Select  BookingNo, BookingDate, Description, Destination, " +
                    "TripStart, TripEnd, BasePrice, AgencyCommission, (BasePrice+AgencyCommission) As TotalPrice, " +
                    "CONCAT(CustFirstName,' ',CustLastName) as Customer  \n" +
                    "FROM BookingDetails JOIN Bookings \n" +
                    "ON BookingDetails.BookingId = Bookings.BookingId\n" +
                    "JOIN Customers ON Customers.CustomerId = Bookings.CustomerId");
            ResultSetMetaData rsmd = rsBookings.getMetaData();
            ObservableList<String> bookings;
            ObservableList<ObservableList> bookingsList = FXCollections.observableArrayList();

            while (rsBookings.next()) {
                bookings=FXCollections.observableArrayList();
                for(int i=1 ; i<=rsmd.getColumnCount(); i++){
                    bookings.add(rsBookings.getString(i));

                }
                bookingsList.add(bookings);
            }
            rsBookings.next();

            int colCount = rsmd.getColumnCount();

            tblBookings.getColumns().clear();

            for(int i=0;i<colCount;i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rsmd.getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tblBookings.getColumns().addAll(col);
            }

            tblBookings.setItems(bookingsList);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

