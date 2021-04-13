package classes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Date;

public class BookingDetail {
    private SimpleIntegerProperty BookingDetailId;
    private SimpleFloatProperty ItineraryNo;
    private Date TripStart;
    private Date TripEnd;
    private String Description;
    private String Destination;
    private SimpleDoubleProperty BasePrice;
    private SimpleDoubleProperty AgencyCommission;
    private SimpleIntegerProperty BookingId;
    private String RegionId;
    private String ClassId;
    private String FeeId;
    private SimpleIntegerProperty ProductSupplierId;

    public BookingDetail(int bookingDetailId, float itineraryNo, Date tripStart, Date tripEnd,
                         String description, String destination, double basePrice, double agencyCommission,
                         int bookingId, String regionId, String classId, String feeId,
                         int productSupplierId) {
        BookingDetailId = new SimpleIntegerProperty(bookingDetailId);
        ItineraryNo = new SimpleFloatProperty(itineraryNo);
        TripStart = tripStart;
        TripEnd = tripEnd;
        Description = description;
        Destination = destination;
        BasePrice = new SimpleDoubleProperty(basePrice);
        AgencyCommission = new SimpleDoubleProperty(agencyCommission);
        BookingId = new SimpleIntegerProperty(bookingId);
        RegionId = regionId;
        ClassId = classId;
        FeeId = feeId;
        ProductSupplierId = new SimpleIntegerProperty(productSupplierId);
    }

    public int getBookingDetailId() {
        return BookingDetailId.get();
    }

    public SimpleIntegerProperty bookingDetailIdProperty() {
        return BookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        this.BookingDetailId.set(bookingDetailId);
    }

    public float getItineraryNo() {
        return ItineraryNo.get();
    }

    public SimpleFloatProperty itineraryNoProperty() {
        return ItineraryNo;
    }

    public void setItineraryNo(float itineraryNo) {
        this.ItineraryNo.set(itineraryNo);
    }

    public Date getTripStart() {
        return TripStart;
    }

    public void setTripStart(Date tripStart) {
        TripStart = tripStart;
    }

    public Date getTripEnd() {
        return TripEnd;
    }

    public void setTripEnd(Date tripEnd) {
        TripEnd = tripEnd;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public double getBasePrice() {
        return BasePrice.get();
    }

    public SimpleDoubleProperty basePriceProperty() {
        return BasePrice;
    }

    public void setBasePrice(double basePrice) {
        this.BasePrice.set(basePrice);
    }

    public double getAgencyCommission() {
        return AgencyCommission.get();
    }

    public SimpleDoubleProperty agencyCommissionProperty() {
        return AgencyCommission;
    }

    public void setAgencyCommission(double agencyCommission) {
        this.AgencyCommission.set(agencyCommission);
    }

    public int getBookingId() {
        return BookingId.get();
    }

    public SimpleIntegerProperty bookingIdProperty() {
        return BookingId;
    }

    public void setBookingId(int bookingId) {
        this.BookingId.set(bookingId);
    }

    public String getRegionId() {
        return RegionId;
    }

    public void setRegionId(String regionId) {
        RegionId = regionId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getFeeId() {
        return FeeId;
    }

    public void setFeeId(String feeId) {
        FeeId = feeId;
    }

    public int getProductSupplierId() {
        return ProductSupplierId.get();
    }

    public SimpleIntegerProperty productSupplierIdProperty() {
        return ProductSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.ProductSupplierId.set(productSupplierId);
    }
}
