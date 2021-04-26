/**
 * Sample Skeleton for 'customerBookings.fxml' Controller Class
 */

package controllers;

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
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.*;
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
//            ArrayList bookingsList = new ArrayList();
            while (rsBookings.next()) {
                bookings=FXCollections.observableArrayList();
                for(int i=1 ; i<=rsmd.getColumnCount(); i++){
                    bookings.add(rsBookings.getString(i));
//                    System.out.println(rsBookings.getString(i));
                }

                bookingsList.add(bookings);
//                bookings.clear();
                //System.out.println(bookingsList);

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
//                System.out.println("Column ["+i+"] ");


//                temp = new TableColumn();
//                temp.setText(rsmd.getColumnLabel(i));
//                temp.setCellValueFactory(new PropertyValueFactory<>(rsmd.getColumnLabel(i)));

//                tableViewCustBookings.getColumns().add(temp);
            }

            tableViewCustBookings.setItems(bookingsList);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        btnExport.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                Document document = new Document();
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
                    stmt.setInt(1, customerId);

                    ResultSet rsBookings = stmt.executeQuery();
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

                    //writing bookings to pdf
                    PdfWriter.getInstance(document, new FileOutputStream("CustomerBookings.pdf"));
                    document.open();
                    Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                    Chunk title = new Chunk("Invoice", font);
                    document.add(title);
                    document.add(new Paragraph("\n"));
                    String customerString = customer.toPDF();


                    document.add(new Paragraph(customerString, font));
                    document.add(new Paragraph("\n"));

                    PdfPTable table = new PdfPTable(new float[]{1, 2, 1, 1, 1});
                    PdfPCell cell = new PdfPCell();

                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);//ALIGN_CENTER mandate for all the cell
                    cell.setBorder(Rectangle.NO_BORDER);//No border for this cell mandate for all the cell
                    table.addCell(cell);

                    //for table start(not to use Rectangle.NO_BORDER)

                    PdfPCell PdfPCell;//same cell instance for 1 row

                    PdfPCell = new PdfPCell(new Phrase("Name",font));
                    PdfPCell.setColspan(3);
                    PdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    PdfPCell.setGrayFill(0.8f);
                    table.addCell(PdfPCell); //prepare cell and add to table

                    PdfPCell = new PdfPCell(new Phrase("Dinesh",font));
                    PdfPCell.setColspan(3);
                    PdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(PdfPCell); //prepare cell and add to table

                    //for table end

                    //defining table width for each column
                    Rectangle Rectangle = new Rectangle(275, 770);
                    PdfPTable samplePdfPTable = new PdfPTable(3);
                    samplePdfPTable.setWidthPercentage(new float[] { 40,80, 160 },Rectangle);

                    //space between table using Phrase instance in cell and cell's colspan and fixedheight
                    PdfPCell = new PdfPCell(new Phrase(""));
                    PdfPCell.setColspan(7);
                    PdfPCell.setFixedHeight(25);
                    PdfPCell.setBorder(PdfPCell.NO_BORDER);



                    table.addCell(PdfPCell);

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




}

