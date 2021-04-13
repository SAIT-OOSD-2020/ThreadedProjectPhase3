package classes;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.Date;

public class Booking {
    private SimpleIntegerProperty BookingId;
    private Date BookingDate;
    private String BookingNo;
    private String TravelerCount;
    private SimpleIntegerProperty CustomerId;
    private String TripTypeId;
    private SimpleIntegerProperty PackageId;

    public Booking(int bookingId, Date bookingDate, String bookingNo, String travelerCount,
                   int customerId, String tripTypeId, int packageId) {
        BookingId = new SimpleIntegerProperty(bookingId);
        BookingDate = bookingDate;
        BookingNo = bookingNo;
        TravelerCount = travelerCount;
        CustomerId = new SimpleIntegerProperty(customerId);
        TripTypeId = tripTypeId;
        PackageId = new SimpleIntegerProperty(packageId);
    }

    public int getBookingId() {
        return BookingId.get();
    }


    public void setBookingId(int bookingId) {
        this.BookingId.set(bookingId);
    }

    public Date getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        BookingDate = bookingDate;
    }

    public String getBookingNo() {
        return BookingNo;
    }

    public void setBookingNo(String bookingNo) {
        BookingNo = bookingNo;
    }

    public String getTravelerCount() {
        return TravelerCount;
    }

    public void setTravelerCount(String travelerCount) {
        TravelerCount = travelerCount;
    }

    public int getCustomerId() {
        return CustomerId.get();
    }

    public void setCustomerId(int customerId) {
        this.CustomerId.set(customerId);
    }

    public String getTripTypeId() {
        return TripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        TripTypeId = tripTypeId;
    }

    public int getPackageId() {
        return PackageId.get();
    }

    public void setPackageId(int packageId) {
        this.PackageId.set(packageId);
    }
}
