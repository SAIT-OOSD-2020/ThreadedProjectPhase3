package controllers;

import classes.Package;
import data.MySQLConnectionData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.util.Currency;

public class PackagesController {

    // Table
    @FXML
    private TableView<Package> tblPackages;
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
    private Button btnPackageSave;
    @FXML
    private Button btnPackageDelete;
    @FXML
    private Button btnPackageReset;

    //TextFields
    @FXML
    private TextField txtPkgName;
    @FXML
    private TextField txtPkgStartDate;
    @FXML
    private DatePicker dtpPkgStartDate;
    @FXML
    private TextField txtPkgEndDate;
    @FXML
    private DatePicker dtpPkgEndDate;
    @FXML
    private TextField txtPkgDesc;
    @FXML
    private TextField txtPkgBasePrice;
    @FXML
    private TextField txtPkgAgencyCommission;

    private String editState = "Add";

    @FXML
    void initialize() {

        tableReset();

        btnPackageAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tblPackages.setDisable(true);
                setEditableText(true);
                btnPackageEdit.setDisable(true);
                btnPackageSave.setDisable(false);
                btnPackageAdd.setDisable(true);
                btnPackageDelete.setDisable(true);

                editState = "Add";

            }
        });

        btnPackageEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tblPackages.setDisable(true);
                setEditableText(true);
                btnPackageEdit.setDisable(true);
                btnPackageSave.setDisable(false);
                btnPackageAdd.setDisable(true);

                editState = "Edit";

            }
        });

        btnPackageSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (editState == "Add") {
                    create();
                } else if (editState == "Edit") {
                    update();
                }
                else {
                    System.out.println("Mistakes were made");
                }

                resetForm();
            }
        });

        btnPackageDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEditableText(false);
                btnPackageEdit.setDisable(false);
                btnPackageSave.setDisable(true);
                btnPackageAdd.setDisable(false);
                btnPackageDelete.setDisable(false);
                delete();
                tableReset();
            }
        });

        btnPackageReset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tblPackages.getSelectionModel().clearSelection();
                tblPackages.setDisable(false);
                resetForm();
                setEditableText(false);
                btnPackageEdit.setDisable(true);
                btnPackageSave.setDisable(true);
                btnPackageAdd.setDisable(false);
                btnPackageDelete.setDisable(true);

            }
        });
    }

    private void tableReset() {
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

                    txtPkgName.setText(t1.getPkgName());
                    dtpPkgStartDate.setValue(t1.getPkgStartDate().toLocalDateTime().toLocalDate());
                    dtpPkgEndDate.setValue(t1.getPkgEndDate().toLocalDateTime().toLocalDate());
                    txtPkgStartDate.setText(t1.getPkgStartDate().toString());
                    txtPkgEndDate.setText(t1.getPkgEndDate().toString());
                    txtPkgDesc.setText(t1.getPkgDesc());
                    txtPkgBasePrice.setText(String.valueOf(t1.getPkgBasePrice()));
                    txtPkgAgencyCommission.setText(String.valueOf(t1.getPkgAgencyCommission()));
                    btnPackageAdd.setDisable(true);
                    btnPackageEdit.setDisable(false);
                    btnPackageSave.setDisable(true);
                    btnPackageDelete.setDisable(false);
                }
            });
            conn.close();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void resetForm() {

        tableReset();
        tblPackages.setDisable(false);
        setEditableText(false);
        btnPackageEdit.setDisable(true);
        btnPackageSave.setDisable(true);
        btnPackageAdd.setDisable(false);
        btnPackageDelete.setDisable(false);

        txtPkgName.setText("");
        txtPkgStartDate.setText("");
        txtPkgEndDate.setText("");
        txtPkgDesc.setText("");
        txtPkgBasePrice.setText("");
        txtPkgAgencyCommission.setText("");
    }

    private void setEditableText(boolean bool) {
        if (bool){
            txtPkgName.setDisable(false);
            dtpPkgStartDate.setDisable(false);
            dtpPkgEndDate.setDisable(false);
            txtPkgStartDate.setDisable(false);
            txtPkgEndDate.setDisable(false);
            txtPkgDesc.setDisable(false);
            txtPkgBasePrice.setDisable(false);
            txtPkgAgencyCommission.setDisable(false);

        } else {
            txtPkgName.setDisable(true);
            dtpPkgStartDate.setDisable(true);
            dtpPkgEndDate.setDisable(true);
            txtPkgStartDate.setDisable(true);
            txtPkgEndDate.setDisable(true);
            txtPkgDesc.setDisable(true);
            txtPkgBasePrice.setDisable(true);
            txtPkgAgencyCommission.setDisable(true);

        }
    }

    private void update() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            String query = "UPDATE Packages set PkgName = ?, PkgStartDate = ?, PkgEndDate = ?, PkgDesc = ?, " +
                    "PkgBasePrice = ?,  PkgAgencyCommission = ? where " +
                    "PackageId = ?";

            Package pkg = tblPackages.getSelectionModel().getSelectedItem();

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, txtPkgName.getText());
            stmt.setDate(2, Date.valueOf(dtpPkgStartDate.getValue()));
            stmt.setDate(3, Date.valueOf(dtpPkgEndDate.getValue()));
            stmt.setString(4, txtPkgDesc.getText());
            stmt.setString(5, txtPkgBasePrice.getText());
            stmt.setString(6, txtPkgAgencyCommission.getText());
            stmt.setInt(7, pkg.getPackageId());

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

    private void delete() {
        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            String query = "DELETE FROM Packages WHERE PackageId = ?";

            PreparedStatement stmt = conn.prepareStatement(query);

            Package pkg = tblPackages.getSelectionModel().getSelectedItem();

            stmt.setInt(1, pkg.getPackageId());

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
    }
}
