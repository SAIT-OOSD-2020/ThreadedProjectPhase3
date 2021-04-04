package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class MySQLConnectionData {
    private final String url;
    private final String username;
    private final String password;

    public MySQLConnectionData(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getMySQLConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
