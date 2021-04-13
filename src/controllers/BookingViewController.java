package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import classes.Booking;
import classes.BookingDetail;
import classes.BookingView;
import classes.Package;
import data.MySQLConnectionData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookingViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<BookingView> tblBookings;

    @FXML
    private TableColumn<?, ?> colBookingNo;

    @FXML
    private TableColumn<?, ?> colBookingDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDestination;

    @FXML
    private TableColumn<?, ?> colTripStart;

    @FXML
    private TableColumn<?, ?> colTripEnd;

    @FXML
    private TableColumn<?, ?> colBasePrice;

    @FXML
    private TableColumn<?, ?> colAgencyCommission;

    @FXML
    private TableColumn<?, ?> colTotalPrice;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    void initialize() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            Statement stmt = conn.createStatement();

            ResultSet rsBookingView = stmt.executeQuery("Select  BookingNo, BookingDate, Description, Destination, " +
                    "TripStart, TripEnd, BasePrice, AgencyCommission, (BasePrice+AgencyCommission) As TotalPrice, CustomerId   \n" +
                    "from BookingDetails JOIN Bookings \n" +
                    "on BookingDetails.BookingId = Bookings.BookingId");
            ObservableList<BookingView> bookingViewList = FXCollections.observableArrayList();

            ArrayList listOfBookingViews = new ArrayList();
            while (rsBookingView.next()) {
                bookingViewList.add(new BookingView(rsBookingView.getString(1),rsBookingView.getDate(2),
                        rsBookingView.getString(3),
                        rsBookingView.getString(4), rsBookingView.getDate(5), rsBookingView.getDate(6),
                        rsBookingView.getDouble(7), rsBookingView.getDouble(8), rsBookingView.getInt(9)));
                listOfBookingViews.add(rsBookingView.getString(2));
            }

            colBookingNo.setCellValueFactory(new PropertyValueFactory<>("BookingNo"));
            colBookingDate.setCellValueFactory(new PropertyValueFactory<>("BookingDate"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
            colDestination.setCellValueFactory(new PropertyValueFactory<>("Destination"));
            colTripStart.setCellValueFactory(new PropertyValueFactory<>("TripStart"));
            colTripEnd.setCellValueFactory(new PropertyValueFactory<>("TripEnd"));
            colBasePrice.setCellValueFactory(new PropertyValueFactory<>("BasePrice"));
            colAgencyCommission.setCellValueFactory(new PropertyValueFactory<>("AgencyCommission"));
            colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
            tblBookings.setItems(bookingViewList);

            conn.close();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}