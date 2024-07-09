package by.toukach.restservlet.repository.repositoryImpl;

import by.toukach.restservlet.db.ConnectionManager;
import by.toukach.restservlet.db.ConnectionManagerImpl;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PhoneNumber;
import by.toukach.restservlet.repository.PersonSectionsRepository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.toukach.restservlet.db.PersonSectionsDBQueries.*;

public class PersonSectionsRepositoryImpl implements PersonSectionsRepository {

    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private static PersonSectionsRepository instance;

    private PersonSectionsRepositoryImpl() {
    }

    public static synchronized PersonSectionsRepository getInstance() {
        if (instance == null) {
            instance = new PersonSectionsRepositoryImpl() {
            };
        }
        return instance;
    }

    private static PersonSection createPersonSection(ResultSet resultSet) throws SQLException {

        PersonSection personSection = new PersonSection(
                resultSet.getInt("section_id"),
                resultSet.getString("section_name"));
        return personSection;
    }

    @Override
    public PersonSection save(PersonSection personSection) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, personSection.getSectionName());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                personSection = new PersonSection(
                        resultSet.getInt("section_id"),
                        personSection.getSectionName()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personSection;
    }

    @Override
    public void update(PersonSection personSection) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, personSection.getSectionName());
            preparedStatement.setInt(2, personSection.getSectionId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteById(int personSectionId) {
        boolean deleteResult = true;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setInt(1, personSectionId);

            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteResult;
    }

    @Override
    public List<PersonSection> findAllBYPersonId(int personId) {
        List<PersonSection> personSection = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setInt(1, personId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                personSection.add(PersonSectionsRepositoryImpl.createPersonSection(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personSection;
    }

    @Override
    public List<PersonSection> findAll() {
        List<PersonSection> personSectionList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                personSectionList.add(createPersonSection(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personSectionList;
    }

    public Optional<PersonSection> findById(int personSectionId) {
        PersonSection personSection = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setInt(1, personSectionId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                personSection = createPersonSection(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(personSection);
    }

    @Override
    public boolean exitsById(int personSectionId) {
        boolean isExists = false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EXIST_BY_ID_SQL)) {

            preparedStatement.setInt(1, personSectionId);

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

