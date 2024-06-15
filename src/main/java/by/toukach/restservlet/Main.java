package by.toukach.restservlet;

import by.toukach.restservlet.db.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static by.toukach.restservlet.db.PersonsDataBaseQueries.FIND_BY_ID_SQL;

public class Main {
    public static void main(String[] args) throws SQLException {

        try (Connection connection = ConnectionManager.open();
             // System.out.println(connection.getTransactionIsolation());
            PreparedStatement preparedStatement=connection.prepareStatement(FIND_BY_ID_SQL))
        {

            preparedStatement.setLong(1, 1);

            ResultSet resultSet = preparedStatement.executeQuery();
        }
    }
}
