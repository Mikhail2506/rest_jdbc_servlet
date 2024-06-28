package by.toukach.restservlet;

import by.toukach.restservlet.db.ConnectionManager;
import by.toukach.restservlet.db.ConnectionManagerImpl;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PhoneNumber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
        try (Connection connection = connectionManager.getConnection();) {
            System.out.println(connection.getTransactionIsolation());
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("""
                            SELECT name, surname, age FROM public.persons;""");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String age = resultSet.getString("age");
             //   List<PhoneNumber> phones = resultSet.;
                System.out.println(name);
                System.out.println(surname);
                System.out.println(age);
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
