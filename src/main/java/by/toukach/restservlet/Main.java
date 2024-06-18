package by.toukach.restservlet;

import by.toukach.restservlet.db.ConnectionManager;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        try (Connection connection = ConnectionManager.open()) {
            System.out.println(connection.getTransactionIsolation());

        }

    }
}
