package by.toukach.restservlet.repository.repositoryImpl;

import by.toukach.restservlet.db.ConnectionManager;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PersonToSection;
import by.toukach.restservlet.repository.PersonSectionsRepository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.toukach.restservlet.db.PersonSectionsDBQueries.*;

public class PersonSectionsRepositoryImpl implements PersonSectionsRepository {

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
                resultSet.getLong("id"),
                resultSet.getString("name"),
                null);
        return personSection;
    }

    @Override
    public PersonSection save(PersonSection personSection) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, personSection.getSectionName());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                personSection = new PersonSection(
                        resultSet.getLong("id"),
                        personSection.getSectionName(),
                        null
                );
                personSection.getPersonList();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personSection;
    }

    @Override
    public void update(PersonSection personSection) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, personSection.getSectionName());
            preparedStatement.setLong(2, personSection.getSectionId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteById(Long personSectionId) {
        boolean deleteResult = true;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setLong(1, personSectionId);

            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteResult;
    }

//    public List<PersonSection> findAllBYPersonId(Long personId) {
//        PersonSection personSection = null;
//        try (Connection connection = ConnectionManager.open();
//             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
//
//            preparedStatement.setLong(1, personId);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                personSection = PersonSectionsRepositoryImpl.createPersonSection(resultSet);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return (List<PersonSection>) personSection;
//    }

    @Override
    public List<PersonSection> findAll() {
        List<PersonSection> personSectionList = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
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

    public Optional<PersonSection> findById(Long personSectionId) {
        PersonSection personSection = null;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, personSectionId);

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
    public PersonToSection save(PersonToSection personToSection) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, personToSection.getId());
            preparedStatement.setLong(2, personToSection.getSectionId());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                personToSection = new PersonToSection(
                        resultSet.getLong("persons_sections_id"),
                        personToSection.getPersonId(),
                        personToSection.getSectionId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personToSection;
    }

    @Override
    public boolean exitsById(Long personSectionId) {
        boolean isExists = false;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(EXIST_BY_ID_SQL)) {

            preparedStatement.setLong(1, personSectionId);

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

