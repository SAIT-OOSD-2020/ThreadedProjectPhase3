package classes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Date;

public class BookingPDFModel {
    private String BookingNo;
    private Date BookingDate;
    private String Description;
    private String Destination;
    private Date TripStart;
    private Date TripEnd;
    private SimpleDoubleProperty BasePrice;
    private SimpleDoubleProperty AgencyCommission;
    private SimpleDoubleProperty TotalPrice;

/*    @Override
    public String ToString() {
        return " ";
    }*/

    public String getBookingNo() {
        return BookingNo;
    }

    public void setBookingNo(String bookingNo) {
        BookingNo = bookingNo;
    }

    public Date getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        BookingDate = bookingDate;
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

    public double getTotalPrice() {
        return TotalPrice.get();
    }

    public SimpleDoubleProperty totalPriceProperty() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.TotalPrice.set(totalPrice);
    }
}
