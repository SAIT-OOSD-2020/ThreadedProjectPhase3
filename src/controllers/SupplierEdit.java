package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import classes.Supplier;
import data.MySQLConnectionData;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SupplierEdit {

    @FXML
    private TextField txtSupId, txtSupName;

    @FXML
    private Button btnSave, btnCancel;

    private Products_SuppliersController parentSupplierCtrl;

    private int selectedSupplierIndex = -1;

    @FXML
    void initialize() {
        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int supId = Integer.parseInt(txtSupId.getText());
                String newSupName = txtSupName.getText();

                if (newSupName.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Supplier name cannot be empty!");
                    alert.showAndWait();
                } else {
                    try {
                        MySQLConnectionData MySQL = new MySQLConnectionData();
                        Connection conn = MySQL.getMySQLConnection();

                        String sql = "UPDATE Suppliers SET\n" +
                                "`SupName` = ?\n" +
                                "WHERE `SupplierId` = ?;";

                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, newSupName);
                        stmt.setInt(2, supId);

                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Update successfully");
                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    parentSupplierCtrl.loadSupplierData();
                    // Set focus to new edited supplier.
                    parentSupplierCtrl.lstSuppliers.scrollTo(selectedSupplierIndex);
                    parentSupplierCtrl.lstSuppliers.getSelectionModel().select(selectedSupplierIndex);

                    Stage stage = (Stage) btnSave.getScene().getWindow();

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
        parentSupplierCtrl = currCtrl;
    }

    public void passCurrSupplier(int selectedSup) {
        selectedSupplierIndex = selectedSup;

        Supplier currSup = parentSupplierCtrl.fullSupplierList.get(selectedSupplierIndex);

        txtSupId.setText(currSup.getSupplierId()+"");
        txtSupName.setText(currSup.getSupName());
    }
}
