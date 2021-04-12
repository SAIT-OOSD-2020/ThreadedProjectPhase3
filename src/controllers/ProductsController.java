package controllers;


import data.MySQLConnectionData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ProductsController {

    // Controls

    @FXML
    private ListView lstProducts;
    @FXML
    private Button btnProductDelete;
    @FXML
    private Button btnProductEdit;
    @FXML
    private Button btnProductAdd;

    private ProductAdd childAddController;

    private ProductEdit childEditController;

    @FXML
    void initialize() {

        loadProductData();
        lstProducts.getSelectionModel().select(0);
        ProductsController currCtrl = this;
        btnProductAddClickedEvent();
        //btnProductEditClickedEvent();
        //btnProductDeleteClickedEvent();
    }

    protected void loadProductData() {
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

            lstProducts.setItems(intList);

            conn.close();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private void btnProductAddClickedEvent() {
        ProductsController currCtrl = this;

        btnProductAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/productAdd.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                childAddController = loader.getController();
                childAddController.setParentController(currCtrl);


                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.setScene(new Scene(root));
                popupStage.setTitle("Add New Product");

                popupStage.show();

            }
        });
    }

//    private void btnProductEditClickedEvent() {
//    }
//
//    private void btnProductDeleteClickedEvent() {
//    }



}
