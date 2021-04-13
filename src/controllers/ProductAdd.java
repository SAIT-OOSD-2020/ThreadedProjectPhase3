package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import data.MySQLConnectionData;
import dataValidation.Validator;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProductAdd {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNewProdName;

    @FXML
    private Button btnAddNewProduct;

    @FXML
    private Button btnCancel;

    private ProductsController parentProductCtrl;

    private int nextId;

    public void NextId(int id) {
        nextId = id;
    }

    public void setParentController(ProductsController parentCtrl) {
        parentProductCtrl = parentCtrl;
    }

    @FXML
    void initialize() {

        btnAddNewProduct.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String newProdName = txtNewProdName.getText();

                Validator valid = new Validator();
                boolean isvalid = valid.IsPresentValidator(txtNewProdName, "Product Name");// && valid.IsNonNumericValidator(txtProdName, "Product Name");

                if (isvalid) {
                    try {
                        MySQLConnectionData MySQL = new MySQLConnectionData();
                        Connection conn = MySQL.getMySQLConnection();

                        String sql = "INSERT INTO Products(`ProductId`,`ProdName`) VALUES\n" +
                                "(?, ?);";

                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, nextId);
                        stmt.setString(2, newProdName);
                        System.out.println(nextId);
                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Insert successfully");
                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    parentProductCtrl.loadProductData();

                    Stage stage = (Stage) btnAddNewProduct.getScene().getWindow();

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


}