/**
 * Sample Skeleton for 'customerBookings.fxml' Controller Class
 */

package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.net.URL;
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
    private TableView<?> tableViewCustBookings; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tableViewCustBookings != null : "fx:id=\"tableViewCustBookings\" was not injected: check your FXML file 'customerBookings.fxml'.";



    }

}
