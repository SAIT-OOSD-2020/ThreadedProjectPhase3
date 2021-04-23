package classes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.sql.Timestamp;

import java.util.Date;

public class Package {
    private SimpleIntegerProperty PackageId;
    private String PkgName;
    private Timestamp PkgStartDate;
    private Timestamp PkgEndDate;
    private String PkgDesc;
    private SimpleDoubleProperty PkgBasePrice;
    private SimpleDoubleProperty PkgAgencyCommission;

    public Package(int packageId, String pkgName, Timestamp pkgStartDate, Timestamp pkgEndDate,
                   String pkgDesc, double pkgBasePrice, double pkgAgencyCommission) {
        PackageId = new SimpleIntegerProperty(packageId);
        PkgName = pkgName;
        PkgStartDate = pkgStartDate;
        PkgEndDate = pkgEndDate;
        PkgDesc = pkgDesc;
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
        return PkgName;
    }

    public void setPkgName(String pkgName) {
        PkgName = pkgName;
    }

    public Timestamp getPkgStartDate() {
        return PkgStartDate;
    }

    public void setPkgStartDate(Timestamp pkgStartDate) {
        PkgStartDate = pkgStartDate;
    }

    public Timestamp getPkgEndDate() {
        return PkgEndDate;
    }

    public void setPkgEndDate(Timestamp pkgEndDate) {
        PkgEndDate = pkgEndDate;
    }

    public String getPkgDesc() {
        return PkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        PkgDesc = pkgDesc;
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

    @Override
    public String toString() {
        return PkgName + "";
    }
}
