package by.toukach.restservlet;

import by.toukach.restservlet.db.ConnectionManager;
import by.toukach.restservlet.entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        List<Person> personList = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            System.out.println(connection.getTransactionIsolation());
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("""
                            SELECT surname FROM public.persons;""");

                ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("surname");
                System.out.println(name);
            }
        }
    }
}
