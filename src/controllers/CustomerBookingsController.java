/**
 * Sample Skeleton for 'customerBookings.fxml' Controller Class
 */

package controllers;

import classes.BookingPDFModel;
import classes.Customer;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import data.MySQLConnectionData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CustomerBookingsController {

    private Customer customer;
    private int customerId;

    public CustomerBookingsController(Customer customer) {
        this.customer = customer;
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tableViewCustBookings"
    private TableView<ObservableList> tableViewCustBookings; // Value injected by FXMLLoader

    @FXML
    Button btnExport;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tableViewCustBookings != null : "fx:id=\"tableViewCustBookings\" was not injected: check your FXML file 'customerBookings.fxml'.";


        try {
            MySQLConnectionData MySQL = new MySQLConnectionData();
            Connection conn = MySQL.getMySQLConnection();
            Statement stmt = conn.createStatement();


            // Display values for Customers tab
            ResultSet rsBookings = stmt.executeQuery("SELECT b.BookingNo, b.BookingDate, bd.Description, bd" +
                    ".Destination,bd.TripStart, bd.TripEnd, bd.BasePrice,bd.AgencyCommission, (bd.BasePrice+bd" +
                    ".AgencyCommission) as TotalPrice FROM bookingDetails as bd Join " +
                    "bookings " +
                    "as b on " +
                    "bd.BookingId = b" +
                    ".BookingId where CustomerId = "+customer.getCustomerId());
            ResultSetMetaData rsmd = rsBookings.getMetaData();
            ObservableList<String> bookings;
            ObservableList<ObservableList> bookingsList = FXCollections.observableArrayList();

            while (rsBookings.next()) {
                bookings=FXCollections.observableArrayList();
                for(int i=1 ; i<=rsmd.getColumnCount(); i++){
                    bookings.add(rsBookings.getString(i));

                }

                bookingsList.add(bookings);


            }
            rsBookings.next();

            int colCount = rsmd.getColumnCount();

            TableColumn temp;
            tableViewCustBookings.getColumns().clear();

            for(int i=0;i<colCount;i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rsmd.getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableViewCustBookings.getColumns().addAll(col);

            }

            tableViewCustBookings.setItems(bookingsList);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        btnExport.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                Document document = new Document(PageSize.A4);
                try {
                    MySQLConnectionData MySQL = new MySQLConnectionData();
                    Connection conn = MySQL.getMySQLConnection();
                    String query = "SELECT b.BookingNo, b.BookingDate, bd.Description, bd.Destination, " +
                            "bd.TripStart, bd.TripEnd, bd.BasePrice, bd.AgencyCommission, " +
                            "(bd.BasePrice + bd.AgencyCommission) as TotalPrice " +
                            "FROM bookingDetails as bd " +
                            "JOIN bookings as b on bd.BookingId = b.BookingId " +
                            "WHERE CustomerId = ? ";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setInt(1, customer.getCustomerId());

                    ResultSet rsBookings = stmt.executeQuery();
                    ArrayList<BookingPDFModel> bookings = new ArrayList<>();

                    while (rsBookings.next()) {
                        bookings.add(new BookingPDFModel(rsBookings.getString(1),
                                rsBookings.getTimestamp(2),
                                rsBookings.getString(3),
                                rsBookings.getString(4),
                                rsBookings.getTimestamp(5),
                                rsBookings.getTimestamp(6),
                                rsBookings.getDouble(7),
                                rsBookings.getDouble(8),
                                rsBookings.getDouble(9)
                        ));
                    }
                    rsBookings.next();
                    conn.close();


                    //writing customer details to pdf
                    PdfWriter.getInstance(document, new FileOutputStream("CustomerBookings.pdf"));
                    document.open();
                    Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

                    document.add(new Paragraph("Invoice", font));
                    document.add(new Paragraph("\n"));
                    document.add(new Paragraph(customer.toPDF(), font));
                    document.add(new Paragraph("\n"));

                    //write bookings to PDF
                    PdfPTable table = new PdfPTable(new float[]{2, 2, 3});
                    table.setTotalWidth(PageSize.A4.getWidth() - 100);
                    table.setLockedWidth(true);
                    DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT);

                    for (int i=0; i<bookings.size(); i++) {
                        BookingPDFModel currentBooking = bookings.get(i);

                        // Row 1
                        table.addCell(cellHelper("Booking Number"));
                        table.addCell(cellHelper("Booking Date"));
                        table.addCell(cellHelper(dateFormatter.format(currentBooking.getBookingDate())));
                        // Row 2
                        table.addCell(cellHelper(currentBooking.getBookingNo()));
                        table.addCell(cellHelper("TripStart"));
                        table.addCell(cellHelper(dateFormatter.format(currentBooking.getTripStart())));
                        // Row 3
                        table.addCell(cellEmpty());
                        table.addCell(cellHelper("TripEnd"));
                        table.addCell(cellHelper(dateFormatter.format(currentBooking.getTripEnd())));
                        // Row 4
                        table.addCell(cellEmpty());
                        table.addCell(cellHelper("Destination"));
                        table.addCell(cellHelper(currentBooking.getDestination()));
                        // Row 5
                        table.addCell(cellEmpty());
                        table.addCell(cellHelper("Description"));
                        table.addCell(cellHelper(currentBooking.getDescription()));
                        // Row 6
                        table.addCell(cellEmpty());
                        table.addCell(cellHelper("BasePrice"));
                        table.addCell(cellHelper(String.valueOf(currentBooking.getBasePrice())));
                        // Row 7
                        table.addCell(cellEmpty());
                        table.addCell(cellHelper("AgencyCommission"));
                        table.addCell(cellHelper(String.valueOf(currentBooking.getAgencyCommission())));
                        // Row 8
                        table.addCell(cellEmpty());
                        table.addCell(cellHelper("TotalPrice"));
                        table.addCell(cellHelper(String.valueOf(currentBooking.getTotalPrice())));
                        // Empty row spacer
                        table.addCell(rowBottomBorder());
                    }

                    document.add(table);

                    document.close();

                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    private PdfPCell cellHelper(String s) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        PdfPCell currentCell = new PdfPCell(new Phrase(s, font));
        currentCell.setColspan(1);
        currentCell.setBorder(Rectangle.NO_BORDER);
        currentCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return currentCell;
    }


    private PdfPCell cellEmpty() {
        PdfPCell cell = new PdfPCell();
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);//ALIGN_CENTER mandate for all the cell
        cell.setBorder(Rectangle.NO_BORDER);//No border for this cell mandate for all the cell
        return cell;
    }
    private PdfPCell rowBottomBorder() {
        PdfPCell cell = new PdfPCell(new Phrase("\n"));
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);//ALIGN_CENTER mandate for all the cell
        cell.setBorder(Rectangle.BOTTOM);//No border for this cell mandate for all the cell
        return cell;
    }
}

