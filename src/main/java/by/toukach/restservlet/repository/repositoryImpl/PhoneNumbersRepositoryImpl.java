package by.toukach.restservlet.repository.repositoryImpl;

import by.toukach.restservlet.db.ConnectionManager;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PhoneNumber;
import by.toukach.restservlet.repository.PhoneNumbersRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static by.toukach.restservlet.db.PhoneNumbersDBQueries.*;

public class PhoneNumbersRepositoryImpl implements PhoneNumbersRepository {


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

        Person person = new Person(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getLong("age"),
                List.of(),
                List.of()
        );
        phoneNumber = new PhoneNumber(
                resultSet.getLong("id"),
                resultSet.getString("phone"),
                person);
        return phoneNumber;
    }

    @Override
    public PhoneNumber save(PhoneNumber phoneNumber) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, phoneNumber.getNumber());
            if (phoneNumber.getPerson() == null) {
                preparedStatement.setNull(2, Types.NULL);
            } else {
                preparedStatement.setLong(2, phoneNumber.getPerson().getPersonId());
            }
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {

                phoneNumber = new PhoneNumber(
                        resultSet.getLong("id"),
                        phoneNumber.getNumber(),
                        phoneNumber.getPerson()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneNumber;
    }

    @Override
    public void update(PhoneNumber phoneNumber) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, phoneNumber.getNumber());
            if (phoneNumber.getPerson() == null) {
                preparedStatement.setNull(2, Types.NULL);
            } else {
                preparedStatement.setLong(2, phoneNumber.getPerson().getPersonId());
            }
            preparedStatement.setLong(3, phoneNumber.getPhoneNumberId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteById(Long id) {
        boolean deleteResult = true;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setLong(1, id);

            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteResult;
    }

    @Override
    public boolean exists(String number) {
        boolean isExists = false;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(EXIST_BY_NUMBER_SQL)) {

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
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NUMBER_SQL)) {

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
    public List<PhoneNumber> findAllByPersonId(Long id) {
        List<PhoneNumber> phoneNumberList = new CopyOnWriteArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                phoneNumberList.add(PhoneNumbersRepositoryImpl.createPhoneNumber(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneNumberList;
    }

    @Override
    public boolean exitsById(int id) {
        boolean isExists = false;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(EXIST_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isExists = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExists;
    }
}
