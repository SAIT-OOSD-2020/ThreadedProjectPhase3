//package controllers;
//
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.ResourceBundle;
//
//import classes.Booking;
//import classes.BookingDetail;
//import classes.Package;
//import data.MySQLConnectionData;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//
//public class BookingsController {
//
//    @FXML
//    private ResourceBundle resources;
//
//    @FXML
//    private URL location;
//
//    @FXML
//    private TableView<Booking> tblBookings;
//
//    @FXML
//    private TableColumn<?, ?> colBookingNo;
//
//    @FXML
//    private TableColumn<?, ?> colBookingDate;
//
//    @FXML
//    private TableColumn<?, ?> colDescription;
//
//    @FXML
//    private TableColumn<?, ?> colDestination;
//
//    @FXML
//    private TableColumn<?, ?> colTripStart;
//
//    @FXML
//    private TableColumn<?, ?> colTripEnd;
//
//    @FXML
//    private TableColumn<?, ?> colBasePrice;
//
//    @FXML
//    private TableColumn<?, ?> colAgencyCommission;
//
//    @FXML
//    private TableColumn<?, ?> colTotalPrice;
//
//    @FXML
//    private TableColumn<?, ?> colCustomerId;
//
//    @FXML
//    void initialize() {
//        try {
//            MySQLConnectionData MySQL = new MySQLConnectionData();
//            Connection conn = MySQL.getMySQLConnection();
//            Statement stmt = conn.createStatement();
//
//            ResultSet rsBookings = stmt.executeQuery("SELECT * FROM Bookings");
//            //ResultSet rsBookingDetails = stmt.executeQuery("SELECT * FROM BookingDetails");
//            ObservableList<Booking> bookingList = FXCollections.observableArrayList();
//
//            ArrayList listOfBookings = new ArrayList();
//            while (rsBookings.next()) {
//                bookingList.add(new Booking(rsBookings.getInt(1), rsBookings.getDate(2), rsBookings.getString(3),
//                        rsBookings.getString(4), rsBookings.getInt(5), rsBookings.getString(6),
//                        rsBookings.getInt(7)));
//                listOfBookings.add(rsBookings.getString(2));
//            }
//
//            colBookingNo.setCellValueFactory(new PropertyValueFactory<>("BookingNo"));
//            colBookingDate.setCellValueFactory(new PropertyValueFactory<>("BookingDate"));
//            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
//            //tblBookings.setItems(bookingList);
//            ObservableList<Integer> booking = FXCollections.observableArrayList(listOfBookings);
//
//            conn.close();
//        } catch (
//                SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//    }
//}

