package classes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Package {
    private SimpleIntegerProperty PackageId;
    private SimpleStringProperty PkgName;
    private Date PkgStartDate;
    private Date PkgEndDate;
    private SimpleStringProperty PkgDesc;
    private SimpleDoubleProperty PkgBasePrice;
    private SimpleDoubleProperty PkgAgencyCommission;

    public Package(int packageId, String pkgName, Date pkgStartDate, Date pkgEndDate,
                   String pkgDesc, double pkgBasePrice, double pkgAgencyCommission) {
        PackageId = new SimpleIntegerProperty(packageId);
        PkgName = new SimpleStringProperty(pkgName);
        PkgStartDate = pkgStartDate;
        PkgEndDate = pkgEndDate;
        PkgDesc = new SimpleStringProperty(pkgDesc);
        PkgBasePrice = new SimpleDoubleProperty(pkgBasePrice);
        PkgAgencyCommission = new SimpleDoubleProperty(pkgAgencyCommission);
    }

    public int getPackageId() {
        return PackageId.get();
    }

    public SimpleIntegerProperty packageIdProperty() {
        return PackageId;
    }

    public void setPackageId(int packageId) {
        this.PackageId.set(packageId);
    }

    public String getPkgName() {
        return PkgName.get();
    }

    public SimpleStringProperty pkgNameProperty() {
        return PkgName;
    }

    public void setPkgName(String pkgName) {
        this.PkgName.set(pkgName);
    }

    public Date getPkgStartDate() {
        return PkgStartDate;
    }

    public void setPkgStartDate(Date pkgStartDate) {
        PkgStartDate = pkgStartDate;
    }

    public Date getPkgEndDate() {
        return PkgEndDate;
    }

    public void setPkgEndDate(Date pkgEndDate) {
        PkgEndDate = pkgEndDate;
    }

    public String getPkgDesc() {
        return PkgDesc.get();
    }

    public SimpleStringProperty pkgDescProperty() {
        return PkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        this.PkgDesc.set(pkgDesc);
    }

    public double getPkgBasePrice() {
        return PkgBasePrice.get();
    }

    public SimpleDoubleProperty pkgBasePriceProperty() {
        return PkgBasePrice;
    }

    public void setPkgBasePrice(double pkgBasePrice) {
        this.PkgBasePrice.set(pkgBasePrice);
    }

    public double getPkgAgencyCommission() {
        return PkgAgencyCommission.get();
    }

    public SimpleDoubleProperty pkgAgencyCommissionProperty() {
        return PkgAgencyCommission;
    }

    public void setPkgAgencyCommission(double pkgAgencyCommission) {
        this.PkgAgencyCommission.set(pkgAgencyCommission);
    }
}
