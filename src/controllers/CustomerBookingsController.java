/**
 * Sample Skeleton for 'customerBookings.fxml' Controller Class
 */

package controllers;

import data.MySQLConnectionData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerBookingsController {

    private int customerId;
    public CustomerBookingsController(int customerId) {

        this.customerId = customerId;
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tableViewCustBookings"
    private TableView<ObservableList> tableViewCustBookings; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tableViewCustBookings != null : "fx:id=\"tableViewCustBookings\" was not injected: check your FXML file 'customerBookings.fxml'.";


        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            Statement stmt = conn.createStatement();


            // Display values for Customers tab
            ResultSet rsBookings = stmt.executeQuery("SELECT b.BookingNo, b.BookingDate, bd.Description, bd" +
                    ".Destination,bd.TripStart, bd.TripEnd, bd.BasePrice,bd.AgencyCommission, (bd.BasePrice+bd" +
                    ".AgencyCommission) as TotalPrice FROM bookingDetails as bd Join " +
                    "bookings " +
                    "as b on " +
                    "bd.BookingId = b" +
                    ".BookingId where CustomerId = "+customerId);
            ResultSetMetaData rsmd = rsBookings.getMetaData();
            ObservableList<String> bookings;
            ObservableList<ObservableList> bookingsList = FXCollections.observableArrayList();
//            ArrayList bookingsList = new ArrayList();
            while (rsBookings.next()) {
                bookings=FXCollections.observableArrayList();
                for(int i=1 ; i<=rsmd.getColumnCount(); i++){
                    bookings.add(rsBookings.getString(i));
//                    System.out.println(rsBookings.getString(i));
                }

                bookingsList.add(bookings);
//                bookings.clear();
                System.out.println(bookingsList);

            }
            rsBookings.next();

            int colCount = rsmd.getColumnCount();

            TableColumn temp;
            tableViewCustBookings.getColumns().clear();

            for(int i=0;i<colCount;i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rsmd.getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableViewCustBookings.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");


//                temp = new TableColumn();
//                temp.setText(rsmd.getColumnLabel(i));
//                temp.setCellValueFactory(new PropertyValueFactory<>(rsmd.getColumnLabel(i)));

//                tableViewCustBookings.getColumns().add(temp);
            }

            tableViewCustBookings.setItems(bookingsList);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

