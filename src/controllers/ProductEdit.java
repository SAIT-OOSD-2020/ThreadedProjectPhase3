package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import classes.Product;
import classes.Supplier;
import data.MySQLConnectionData;
import dataValidation.Validator;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProductEdit {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtProdName;

    @FXML
    private Button btnEditProduct;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtProductId;

    private Products_SuppliersController parentProductCtrl;

    private int selectedProductIndex = -1;

    @FXML
    void initialize() {
        btnEditProduct.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int productId = Integer.parseInt(txtProductId.getText());
                String prodName = txtProdName.getText();

                Validator valid = new Validator();
                boolean isvalid = valid.IsPresentValidator(txtProdName, "Product Name");// && valid.IsNonNumericValidator(txtProdName, "Product Name");

                if (isvalid) {
                    try {
                        MySQLConnectionData MySQL = new MySQLConnectionData();
                        Connection conn = MySQL.getMySQLConnection();

                        String sql = "UPDATE Products SET\n" +
                                "`ProdName` = ?\n" +
                                "WHERE `ProductId` = ?;";

                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, prodName);
                        stmt.setInt(2, productId);

                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Update successfully");
                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    parentProductCtrl.loadProductData();

                    Stage stage = (Stage) btnEditProduct.getScene().getWindow();

                    stage.close();

                }
            }
        });

        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) btnCancel.getScene().getWindow();
                stage.close();
            }
        });


    }

    public void setParentController(Products_SuppliersController currCtrl) {
        parentProductCtrl = currCtrl;
    }

    public void passCurrProduct(int selectedProd){
        selectedProductIndex = selectedProd;

        Product currProd = parentProductCtrl.fullProductList.get(selectedProductIndex);

        txtProductId.setText(currProd.getProductId()+"");
        txtProdName.setText(currProd.getProdName());
    }
}
