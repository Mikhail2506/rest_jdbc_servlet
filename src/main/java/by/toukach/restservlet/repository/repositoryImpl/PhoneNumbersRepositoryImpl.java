package by.toukach.restservlet.repository.repositoryImpl;

import by.toukach.restservlet.db.ConnectionManager;
import by.toukach.restservlet.db.ConnectionManagerImpl;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PhoneNumber;
import by.toukach.restservlet.repository.PhoneNumbersRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.toukach.restservlet.db.PhoneNumbersDBQueries.*;

public class PhoneNumbersRepositoryImpl implements PhoneNumbersRepository {

    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private static PhoneNumbersRepository instance;

    private PhoneNumbersRepositoryImpl() {
    }

    public static synchronized PhoneNumbersRepository getInstance() {
        if (instance == null) {
            instance = new PhoneNumbersRepositoryImpl();
        }
        return instance;
    }

    private static PhoneNumber createPhoneNumber(ResultSet resultSet) throws SQLException {
        PhoneNumber phoneNumber;

        Person person = new Person(resultSet.getInt("id"),
                null,

                null,

                null,

                List.of(),
                List.of());

        phoneNumber = new PhoneNumber(resultSet.getInt("id"), resultSet.getString("phone"));
        return phoneNumber;
    }

    @Override
    public PhoneNumber save(PhoneNumber phoneNumber) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, phoneNumber.getNumber());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {

                phoneNumber = new PhoneNumber(resultSet.getInt("id"), phoneNumber.getNumber());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneNumber;
    }

    @Override
    public void update(PhoneNumber phoneNumber) {
        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, phoneNumber.getNumber());

            preparedStatement.setLong(3, phoneNumber.getPhoneNumberId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteById(int id) {
        boolean deleteResult = true;
        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setInt(1, id);

            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteResult;
    }

    @Override
    public boolean exists(String number) {
        boolean isExists = false;
        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(EXIST_BY_NUMBER_SQL)) {

            preparedStatement.setString(1, number);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isExists = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExists;
    }

    @Override
    public Optional<PhoneNumber> findByNumber(String number) {
        PhoneNumber phoneNumber = null;
        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NUMBER_SQL)) {

            preparedStatement.setString(1, number);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                phoneNumber = createPhoneNumber(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(phoneNumber);
    }

    @Override
    public List<PhoneNumber> findAllByPersonId(int personId) {
        List<PhoneNumber> phoneNumberList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USERID_SQL)) {
            preparedStatement.setInt(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                phoneNumberList.add(PhoneNumbersRepositoryImpl.createPhoneNumber(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneNumberList;
    }
}
