package controllers;

import classes.Package;
import classes.Supplier;
import data.MySQLConnectionData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SuppliersController {

    @FXML
    protected ListView lstSuppliers;
    @FXML
    private ComboBox cmbSuppliers;

    @FXML
    private Button btnAdd, btnEdit, btnDelete;

    @FXML
    private TextField txtSearch;

    private SupplierAdd childAddController;

    private SupplierEdit childEditController;

    private List<String> supplierNameList;


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
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (supplierNameList.contains(t1)){
                    System.out.println(t1);
                    int index = supplierNameList.indexOf(t1);
                    lstSuppliers.scrollTo(index);
                    lstSuppliers.getSelectionModel().select(index);
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

                childEditController.passCurrSupplier(selectedSupIndex);

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

//                popupStage.setOnHidden(new EventHandler<WindowEvent>() {
//                    @Override
//                    public void handle(WindowEvent windowEvent) {
////                        loadSupplierData();
//
////                        // Set focus to new added supplier.
////                        lstSuppliers.scrollTo(lstSuppliers.getItems().size() - 1);
////                        lstSuppliers.getSelectionModel().select(lstSuppliers.getItems().size() - 1);
//                    }
//                });

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
            ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
            ArrayList listOfSuppliers = new ArrayList();
            while (rsSuppliers.next()) {
                supplierList.add(new Supplier(rsSuppliers.getInt(1), rsSuppliers.getString(2)));
                listOfSuppliers.add(rsSuppliers.getString(2));
            }
            ObservableList<Integer> sup = FXCollections.observableArrayList(listOfSuppliers);
            cmbSuppliers.getItems().addAll(sup);

            lstSuppliers.setItems(supplierList);

            conn.close();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }

        // Load the name list for searching.
        supplierNameList = new ArrayList<>();
        for(Object supplier: lstSuppliers.getItems()){
            Supplier sup = (Supplier) supplier;
            supplierNameList.add(sup.getSupName());
        }
    }
}
