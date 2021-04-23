package controllers;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import data.MySQLConnectionData;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SupplierAdd {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNewSupName;

    @FXML
    protected Button btnAddNew;

    @FXML
    protected Button btnCancel;

    private int nextId;

    private Products_SuppliersController parentSupplierCtrl;

    @FXML
    void initialize() {
        btnAddNew.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String newSupName = txtNewSupName.getText();

                if (newSupName.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
//                    alert.setHeaderText("Look, an Information Dialog");
                    alert.setContentText("Supplier name cannot be empty!");

                    alert.showAndWait();
                } else {
                    try {
                        MySQLConnectionData MySQL = new MySQLConnectionData();
                        Connection conn = MySQL.getMySQLConnection();
//                        Statement stmt = conn.createStatement();

                        String sql = "INSERT INTO Suppliers(`SupplierId`,`SupName`) VALUES\n" +
                                "(?, ?);";

                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, nextId);
                        stmt.setString(2, newSupName);

                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Insert successfully");
                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    parentSupplierCtrl.loadSupplierData();
                    // Set focus to new added supplier.
                    parentSupplierCtrl.lstSuppliers.scrollTo(parentSupplierCtrl.lstSuppliers.getItems().size() - 1);
                    parentSupplierCtrl.lstSuppliers.getSelectionModel().select(parentSupplierCtrl.lstSuppliers.getItems().size() - 1);

                    Stage stage = (Stage) btnAddNew.getScene().getWindow();

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

    public void NextId(int id) {
        nextId = id;
    }

    public void setParentController(Products_SuppliersController parentCtrl) {
        parentSupplierCtrl = parentCtrl;
    }
}
