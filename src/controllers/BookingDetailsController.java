//package controllers;
//
//import classes.Booking;
//import classes.BookingDetail;
//import data.MySQLConnectionData;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.ResourceBundle;
//
//public class BookingDetailsController {
//
//    @FXML
//    private ResourceBundle resources;
//
//    @FXML
//    private URL location;
//
//    @FXML
//    private TableView<?> tblBookings;
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
//            ResultSet rsBookingDetails = stmt.executeQuery("SELECT * FROM BookingDetails");
//            ObservableList<BookingDetail> bookingDetailList = FXCollections.observableArrayList();
//            ArrayList listOfBookings = new ArrayList();
//
//            while (rsBookingDetails.next()) {
//                bookingDetailList.add(new BookingDetail(rsBookingDetails.getInt(1), rsBookingDetails.getFloat(2),
//                        rsBookingDetails.getDate(3),
//                        rsBookingDetails.getDate(4), rsBookingDetails.getString(5), rsBookingDetails.getString(6),
//                        rsBookingDetails.getDouble(7), rsBookingDetails.getDouble(8), rsBookingDetails.getInt(9),
//                        rsBookingDetails.getString(10), rsBookingDetails.getString(11),
//                        rsBookingDetails.getString(12), rsBookingDetails.getInt(13)));
//                listOfBookings.add(rsBookingDetails.getString(2));
//            }
////
//
//
//            colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
//            colDestination.setCellValueFactory(new PropertyValueFactory<>("Destination"));
//            //colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
//            //tblBookings.setItems(bookingDetailList);
//            ObservableList<Integer> bookingDetail = FXCollections.observableArrayList(listOfBookings);
//
//            conn.close();
//        } catch (
//                SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//    }
//}

