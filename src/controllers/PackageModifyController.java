package controllers;

import classes.Package;
import data.MySQLConnectionData;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import dataValidation.Validator;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PackageModifyController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Button btnPackageSave, btnPackageCancel;

    //TextFields
    @FXML
    private TextField txtPkgName;
    @FXML
    private DatePicker dtpPkgStartDate;
    @FXML
    private DatePicker dtpPkgEndDate;
    @FXML
    private TextField txtPkgDesc;
    @FXML
    private TextField txtPkgBasePrice;
    @FXML
    private TextField txtPkgAgencyCommission;

    private String editState;
    private Package currPkg;
    private PackagesController parentPackageCtrl;
    private int selectedPackageIndex = -1;

    Validator validator = new Validator();

    @FXML
    void initialize() {
        editState = "Add";

        btnPackageSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (validator.IsPresent(txtPkgName, "Package Name") &&
                        validator.IsPresentAndDate(dtpPkgStartDate, "Start Date") &&
                        validator.IsPresentAndDate(dtpPkgEndDate, "End Date") &&
                        validator.IsPresent(txtPkgBasePrice, "Base Price") &&
                        validator.IsNumeric(txtPkgBasePrice, "Base Price") &&
                        (txtPkgAgencyCommission.getText().equals("") ||
                        validator.IsNumeric(txtPkgAgencyCommission, "Agency Commission"))
                ){
                    if (editState == "Add") {
                        create();
                    } else if (editState == "Edit") {
                        update();
                    } else {
                        System.out.println("Mistakes were made");
                    }

                    parentPackageCtrl.tableReset();
                    parentPackageCtrl.tblPackages.getSelectionModel().selectFirst();

                    Stage stage = (Stage) btnPackageSave.getScene().getWindow();
                    stage.close();
                }



            }
        });

        btnPackageCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) btnPackageCancel.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void passCurrPackage(Package currPkg) {
        this.currPkg = currPkg;
        txtPkgName.setText(currPkg.getPkgName());
        dtpPkgStartDate.setValue(currPkg.getPkgStartDate().toLocalDateTime().toLocalDate());
        dtpPkgEndDate.setValue(currPkg.getPkgEndDate().toLocalDateTime().toLocalDate());
        txtPkgDesc.setText(currPkg.getPkgDesc());
        txtPkgBasePrice.setText(String.valueOf(currPkg.getPkgBasePrice()));
        txtPkgAgencyCommission.setText(String.valueOf(currPkg.getPkgAgencyCommission()));
        editState = "Edit";
    }

    private void update() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            String query = "UPDATE Packages set PkgName = ?, PkgStartDate = ?, PkgEndDate = ?, PkgDesc = ?, " +
                    "PkgBasePrice = ?,  PkgAgencyCommission = ? where " +
                    "PackageId = ?";

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, txtPkgName.getText());
            stmt.setDate(2, Date.valueOf(dtpPkgStartDate.getValue()));
            stmt.setDate(3, Date.valueOf(dtpPkgEndDate.getValue()));
            stmt.setString(4, txtPkgDesc.getText());
            stmt.setString(5, txtPkgBasePrice.getText());
            stmt.setString(6, txtPkgAgencyCommission.getText());
            stmt.setInt(7, currPkg.getPackageId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0)
            {
                System.out.println("Package updated");
            }
            else
            {
                System.out.println("Package update failed");
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void create() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            String query = "INSERT INTO Packages (PkgName, PkgStartDate, PkgEndDate, " +
                    "PkgDesc, PkgBasePrice, PkgAgencyCommission) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, txtPkgName.getText());
            stmt.setDate(2, Date.valueOf(dtpPkgStartDate.getValue()));
            stmt.setDate(3, Date.valueOf(dtpPkgEndDate.getValue()));
            stmt.setString(4, txtPkgDesc.getText());
            stmt.setString(5, txtPkgBasePrice.getText());
            stmt.setString(6, txtPkgAgencyCommission.getText());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0)
            {
                System.out.println("Package created");
            }
            else
            {
                System.out.println("Package creation failed");
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setParentController(PackagesController currCtrl) {
        parentPackageCtrl = currCtrl;
    }

}


