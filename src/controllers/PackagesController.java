package controllers;

import classes.Package;
import data.MySQLConnectionData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Currency;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PackagesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    // Table
    @FXML
    protected TableView<Package> tblPackages;
    @FXML
    private TableColumn<Package, String> colPkgName;
    @FXML
    private TableColumn<Package, Date> colPkgStartDate;
    @FXML
    private TableColumn<Package, Date> colPkgEndDate;
    @FXML
    private TableColumn<Package, String> colPkgDesc;
    @FXML
    private TableColumn<Package, Currency> colPkgBasePrice;
    @FXML
    private TableColumn<Package, Currency> colPkgAgencyCommission;

    // Buttons
    @FXML
    private Button btnPackageAdd;
    @FXML
    private Button btnPackageEdit;
    @FXML
    private Button btnPackageDelete;

    private PackageModifyController pkgModifyController;


    @FXML
    void initialize() {

        tableReset();
        tblPackages.getSelectionModel().selectFirst();
        btnAddClickedEvent();
        btnEditClickedEvent();
        btnDeleteClickedEvent();

    }

    private void btnAddClickedEvent() {
        btnPackageAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                // Call packageModify controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/packageModify.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pkgModifyController = loader.getController();

                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);    // lock any other windows of the application
                popupStage.setScene(new Scene(root));
                popupStage.setTitle("Add New Package");

                popupStage.show();
            }
        });
    }

    private void btnEditClickedEvent() {
        btnPackageEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                // Call packageModify controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/packageModify.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pkgModifyController = loader.getController();
                Package currentPkg = tblPackages.getSelectionModel().getSelectedItem();
                pkgModifyController.passCurrPackage(currentPkg);

                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);    // lock any other windows of the application
                popupStage.setScene(new Scene(root));
                popupStage.setTitle("Edit New Package");

                popupStage.show();
            }
        });
    }
    private void btnDeleteClickedEvent() {
        btnPackageDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                delete();
            }
        });
    }
    protected void tableReset() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            Statement stmt = conn.createStatement();

            // Display values for Packages tab
            ResultSet rsPackages = stmt.executeQuery("SELECT * FROM Packages");
            ObservableList<Package> packageList = FXCollections.observableArrayList();
            while (rsPackages.next()) {
                packageList.add(new Package(rsPackages.getInt(1), rsPackages.getString(2),
                        rsPackages.getTimestamp(3),
                        rsPackages.getTimestamp(4),
                        rsPackages.getString(5), rsPackages.getDouble(6),
                        rsPackages.getDouble(7)));
            }

            //colPackageId.setCellValueFactory(new PropertyValueFactory<>("PackageId"));
            colPkgName.setCellValueFactory(new PropertyValueFactory<>("PkgName"));
            colPkgStartDate.setCellValueFactory(new PropertyValueFactory<>("PkgStartDate"));
            colPkgEndDate.setCellValueFactory(new PropertyValueFactory<>("PkgEndDate"));
            colPkgDesc.setCellValueFactory(new PropertyValueFactory<>("PkgDesc"));
            colPkgBasePrice.setCellValueFactory(new PropertyValueFactory<>("PkgBasePrice"));
            colPkgAgencyCommission.setCellValueFactory(new PropertyValueFactory<>("PkgAgencyCommission"));
            tblPackages.setItems(packageList);

            tblPackages.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Package>() {
                @Override
                public void changed(ObservableValue<? extends Package> observableValue, Package o, Package t1) {

                }
            });
            conn.close();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void delete() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            String query = "DELETE FROM Packages WHERE PackageId = ?";

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, tblPackages.getSelectionModel().getSelectedItem().getPackageId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0)
            {
                System.out.println("Package deleted");
            }
            else
            {
                System.out.println("Package delete failed");
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableReset();
    }

}
