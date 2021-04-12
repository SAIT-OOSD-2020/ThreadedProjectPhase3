package controllers;

import classes.Supplier;
import data.MySQLConnectionData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import java.util.List;
import java.util.Optional;

public class SuppliersController {

    @FXML
    protected ListView lstSuppliers;

    @FXML
    private Button btnAdd, btnEdit, btnDelete;

    @FXML
    protected TextField txtSearch;

    private SupplierAdd childAddController;

    private SupplierEdit childEditController;

    protected ObservableList<Supplier> fullSupplierList;


    @FXML
    void initialize() {
        loadSupplierData();
        lstSuppliers.getSelectionModel().select(0);

        btnAddClickedEvent();
        btnEditClickedEvent();
        btnDeleteClickedEvent();

        txtSearchChangedEvent();
    }

    private void txtSearchChangedEvent() {
        txtSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                txtSearch.selectAll();
            }
        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                // When the textField is empty.
                if (t1.isEmpty()){
                    loadSupplierData();
                    lstSuppliers.scrollTo(0);
                    lstSuppliers.getSelectionModel().select(0);

                    btnEdit.setDisable(false);
                    btnDelete.setDisable(false);

                } else {
                    ObservableList<Supplier> tempList = FXCollections.observableArrayList();

                    for (int i = 0; i < fullSupplierList.size(); i++){
                        if (fullSupplierList.get(i).getSupName().matches("(?i).*"+ t1 + ".*")){
                            tempList.add(fullSupplierList.get(i));
                        }
                    }

                    lstSuppliers.setItems(tempList);

                    if (lstSuppliers.getItems().size() == 0){
                        btnEdit.setDisable(true);
                        btnDelete.setDisable(true);
                    } else {
                        lstSuppliers.scrollTo(0);
                        lstSuppliers.getSelectionModel().select(0);

                        btnEdit.setDisable(false);
                        btnDelete.setDisable(false);
                    }
                }
            }
        });
    }

    private void btnDeleteClickedEvent() {
        btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Supplier selectedSup = (Supplier) lstSuppliers.getSelectionModel().getSelectedItem();
                int selectedSupIndex = lstSuppliers.getSelectionModel().getSelectedIndex();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Selected Supplier");
                alert.setHeaderText("This will delete the selected supplier from the database.");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    try {
                        MySQLConnectionData MySQL = new MySQLConnectionData();
                        Connection conn = MySQL.getMySQLConnection();

                        String sql = "DELETE FROM Suppliers \n" +
                                "WHERE `SupplierId` = ?;";

                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, selectedSup.getSupplierId());

                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Delete successfully");
                        }

                        loadSupplierData();

                        // Set focus to next supplier.
                        int newSupSize = lstSuppliers.getItems().size();
                        if (newSupSize == selectedSupIndex){
                            // If the last supplier is deleted, focus on the last one.
                            lstSuppliers.scrollTo(newSupSize-1);
                            lstSuppliers.getSelectionModel().select(newSupSize-1);
                        } else {
                            lstSuppliers.scrollTo(selectedSupIndex);
                            lstSuppliers.getSelectionModel().select(selectedSupIndex);
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

    private void btnEditClickedEvent() {
        SuppliersController currCtrl = this;
        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Call SupplierAdd controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/supplierEdit.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                childEditController = loader.getController();
                childEditController.setParentController(currCtrl);

                int selectedSupIndex = lstSuppliers.getSelectionModel().getSelectedIndex();

//                childEditController.passCurrSupplier(selectedSupIndex);
                childEditController.passCurrSupplier(fullSupplierList.indexOf(lstSuppliers.getSelectionModel().getSelectedItem()));


                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);    // lock any other windows of the application
                popupStage.setScene(new Scene(root));
                popupStage.setTitle("Edit Supplier");
                popupStage.show();
            }
        });
    }

    private void btnAddClickedEvent() {
        SuppliersController currCtrl = this;

        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                // Call SupplierAdd controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/supplierAdd.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                childAddController = loader.getController();
                childAddController.setParentController(currCtrl);

                int nextId = findNextId();

                childAddController.NextId(nextId);

                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);    // lock any other windows of the application
                popupStage.setScene(new Scene(root));
                popupStage.setTitle("Add New Supplier");

                popupStage.show();

            }
        });
    }

    private int findNextId() {
        int size = lstSuppliers.getItems().size();
        Supplier lastSupplier = (Supplier) lstSuppliers.getItems().get(size - 1);
        return lastSupplier.getSupplierId() + 1;
    }

    protected void loadSupplierData() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            Statement stmt = conn.createStatement();

            // Display values for Suppliers tab
            ResultSet rsSuppliers = stmt.executeQuery("SELECT * FROM Suppliers");
            fullSupplierList = FXCollections.observableArrayList();

            while (rsSuppliers.next()) {
                fullSupplierList.add(new Supplier(rsSuppliers.getInt(1), rsSuppliers.getString(2)));
            }


            lstSuppliers.setItems(fullSupplierList);

            conn.close();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
