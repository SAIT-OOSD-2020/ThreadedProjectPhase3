package controllers;

import classes.Product;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class Products_SuppliersController {


    @FXML
    protected ListView lstProducts, lstSuppliers;
    @FXML
    private Button btnProductAdd, btnProductEdit, btnProductDelete;
    @FXML
    private Button btnSupplierAdd, btnSupplierEdit, btnSupplierDelete;
    @FXML
    protected TextField txtSearch, txtProdSearch;

    private SupplierAdd childSupplierAddController;

    private SupplierEdit childSupplierEditController;

    protected ObservableList<Supplier> fullSupplierList;

    private ProductAdd childProductAddController;

    private ProductEdit childProductEditController;

    protected ObservableList<Product> fullProductList;

    @FXML
    void initialize() {

        loadProductData();
        lstProducts.getSelectionModel().select(0);
        Products_SuppliersController currCtrl = this;
        btnProductAddClickedEvent();
        btnProductEditClickedEvent();
        btnProductDeleteClickedEvent();
        txtProdSearchChangedEvent();

        loadSupplierData();
        lstSuppliers.getSelectionModel().select(0);
        btnAddClickedEvent();
        btnEditClickedEvent();
        btnDeleteClickedEvent();

        txtSearchChangedEvent();
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
        Products_SuppliersController currCtrl = this;

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
                childProductAddController = loader.getController();
                childProductAddController.setParentController(currCtrl);


                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.setScene(new Scene(root));
                popupStage.setTitle("Add New Product");

                popupStage.show();

            }
        });
    }

    private void btnProductEditClickedEvent() {
        Products_SuppliersController currCtrl = this;
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

                childProductEditController = loader.getController();
                childProductEditController.setParentController(currCtrl);
                childProductEditController.passCurrProduct(fullProductList.indexOf(lstProducts.getSelectionModel().getSelectedItem()));


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

                    btnSupplierEdit.setDisable(false);
                    btnSupplierDelete.setDisable(false);

                } else {
                    ObservableList<Supplier> tempList = FXCollections.observableArrayList();

                    for (int i = 0; i < fullSupplierList.size(); i++){
                        if (fullSupplierList.get(i).getSupName().matches("(?i).*"+ t1 + ".*")){
                            tempList.add(fullSupplierList.get(i));
                        }
                    }

                    lstSuppliers.setItems(tempList);

                    if (lstSuppliers.getItems().size() == 0){
                        btnSupplierEdit.setDisable(true);
                        btnSupplierDelete.setDisable(true);
                    } else {
                        lstSuppliers.scrollTo(0);
                        lstSuppliers.getSelectionModel().select(0);

                        btnSupplierEdit.setDisable(false);
                        btnSupplierDelete.setDisable(false);
                    }
                }
            }
        });
    }

    private void txtProdSearchChangedEvent() {
        txtProdSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                txtProdSearch.selectAll();
            }
        });

        txtProdSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                // When the textField is empty.
                if (t1.isEmpty()){
                    loadProductData();
                    lstProducts.scrollTo(0);
                    lstProducts.getSelectionModel().select(0);

                    btnProductEdit.setDisable(false);
                    btnProductDelete.setDisable(false);

                } else {
                    ObservableList<Product> tempList = FXCollections.observableArrayList();

                    for (int i = 0; i < fullProductList.size(); i++){
                        if (fullProductList.get(i).getProdName().matches("(?i).*"+ t1 + ".*")){
                            tempList.add(fullProductList.get(i));
                        }
                    }

                    lstProducts.setItems(tempList);

                    if (lstProducts.getItems().size() == 0){
                        btnProductEdit.setDisable(true);
                        btnProductDelete.setDisable(true);
                    } else {
                        lstProducts.scrollTo(0);
                        lstProducts.getSelectionModel().select(0);

                        btnProductEdit.setDisable(false);
                        btnProductDelete.setDisable(false);
                    }
                }
            }
        });
    }

    private void btnDeleteClickedEvent() {
        btnSupplierDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        Products_SuppliersController currCtrl = this;
        btnSupplierEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
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

                childSupplierEditController = loader.getController();
                childSupplierEditController.setParentController(currCtrl);

                int selectedSupIndex = lstSuppliers.getSelectionModel().getSelectedIndex();

//                childEditController.passCurrSupplier(selectedSupIndex);
                childSupplierEditController.passCurrSupplier(fullSupplierList.indexOf(lstSuppliers.getSelectionModel().getSelectedItem()));


                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);    // lock any other windows of the application
                popupStage.setScene(new Scene(root));
                popupStage.setTitle("Edit Supplier");
                popupStage.show();
            }
        });
    }

    private void btnAddClickedEvent() {
        Products_SuppliersController currCtrl = this;

        btnSupplierAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
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

                childSupplierAddController = loader.getController();
                childSupplierAddController.setParentController(currCtrl);

                int nextId = findNextId();

                childSupplierAddController.NextId(nextId);

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
