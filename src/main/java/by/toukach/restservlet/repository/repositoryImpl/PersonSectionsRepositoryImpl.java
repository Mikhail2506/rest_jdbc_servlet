package by.toukach.restservlet.repository.repositoryImpl;

import by.toukach.restservlet.db.ConnectionManager;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PersonToSection;
import by.toukach.restservlet.repository.PersonRepository;
import by.toukach.restservlet.repository.PersonSectionsRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.toukach.restservlet.db.PersonSectionsDBQueries.*;
import static by.toukach.restservlet.db.PersonToSectionDBQueries.FIND_ALL_BY_DEPARTMENT_ID_SQL;

public class PersonSectionsRepositoryImpl implements PersonSectionsRepository {

    private static final PersonRepository personRepository = PersonRepositoryImpl.getInstance();
    private static PersonSectionsRepository instance;
    private final Connection connection = ConnectionManager.open();

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

            preparedStatement.setString(1, personSection.getName());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                personSection = new PersonSection(
                        resultSet.getLong("id"),
                        personSection.getName(),
                        null
                );
                personSection.getPersons();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personSection;
    }

    @Override
    public void update(PersonSection personSection) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);) {

            preparedStatement.setString(1, personSection.getName());
            preparedStatement.setLong(2, personSection.getSectionId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteById(Long id) {
        boolean deleteResult = true;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);) {

            preparedStatement.setLong(1, id);

            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteResult;
    }

    public List<PersonSection> findAllBYPersonId() {
        PersonSection personSection = null;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

           // preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                personSection = PersonSectionsRepositoryImpl.createPersonSection(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (List<PersonSection>) personSection;
    }

    public Optional<PersonSection> findById(Long id) {
        PersonSection personSection = null;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

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
    public boolean exitsById(Long id) {
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

    @Override
    public List<Person> findPersonsBySectionId(Long id) {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_DEPARTMENT_ID_SQL)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                persons.add(personRepository.createPerson(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }
}

