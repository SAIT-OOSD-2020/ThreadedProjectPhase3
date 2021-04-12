package controllers;


import classes.Product;
import classes.Supplier;
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
import java.util.Optional;

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

    protected ObservableList<Product> fullProductList;

    @FXML
    void initialize() {

        loadProductData();
        lstProducts.getSelectionModel().select(0);
        ProductsController currCtrl = this;
        btnProductAddClickedEvent();
        btnProductEditClickedEvent();
        btnProductDeleteClickedEvent();
    }

    protected void loadProductData() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            Statement stmt = conn.createStatement();

            ResultSet rsProducts = stmt.executeQuery("SELECT * FROM Products");
            fullProductList = FXCollections.observableArrayList();

            while (rsProducts.next()) {
                fullProductList.add(new Product(rsProducts.getInt(1), rsProducts.getString(2)));
            }
            lstProducts.setItems(fullProductList);

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

    private void btnProductEditClickedEvent() {
        ProductsController currCtrl = this;
        btnProductEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/productEdit.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                childEditController = loader.getController();
                childEditController.setParentController(currCtrl);
                childEditController.passCurrProduct(fullProductList.indexOf(lstProducts.getSelectionModel().getSelectedItem()));


                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);    // lock any other windows of the application
                popupStage.setScene(new Scene(root));
                popupStage.setTitle("Edit Product");
                popupStage.show();
            }
        });
    }

    private void btnProductDeleteClickedEvent() {
        btnProductDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(lstProducts.getSelectionModel().getSelectedIndex());
                Product selectedProd = (Product) lstProducts.getSelectionModel().getSelectedItem();
                int selectedProdIndex = lstProducts.getSelectionModel().getSelectedIndex();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Product");
                alert.setHeaderText("This will delete the selected product from the database.");
                alert.setContentText("Please confirm deletion of: " + selectedProd);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    try {
                        MySQLConnectionData MySQL = new MySQLConnectionData();
                        Connection conn = MySQL.getMySQLConnection();

                        String sql = "DELETE FROM Products \n" +
                                "WHERE `ProductId` = ?;";

                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, selectedProd.getProductId());

                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Delete successfully");
                        }

                        loadProductData();

                        int newSupSize = lstProducts.getItems().size();
                        if (newSupSize == selectedProdIndex){
                            // If the last product is deleted, focus on the last one.
                            lstProducts.scrollTo(newSupSize-1);
                            lstProducts.getSelectionModel().select(newSupSize-1);
                        } else {
                            lstProducts.scrollTo(selectedProdIndex);
                            lstProducts.getSelectionModel().select(selectedProdIndex);
                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        });

    }



}
