package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class MySQLConnectionData {
    private final String url  = "jdbc:mysql://localhost:3306/travelexperts?zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8";
    private final String username = "root";
    private final String password = "";

    public MySQLConnectionData() {
    }

    public Connection getMySQLConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
