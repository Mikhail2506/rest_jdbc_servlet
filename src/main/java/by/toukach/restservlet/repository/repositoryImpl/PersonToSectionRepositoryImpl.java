package by.toukach.restservlet.repository.repositoryImpl;

import by.toukach.restservlet.db.ConnectionManager;
import by.toukach.restservlet.db.ConnectionManager;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PersonToSection;
import by.toukach.restservlet.repository.PersonRepository;
import by.toukach.restservlet.repository.PersonSectionsRepository;
import by.toukach.restservlet.repository.PersonToSectionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static by.toukach.restservlet.db.PersonToSectionDBQueries.*;
public class PersonToSectionRepositoryImpl implements PersonToSectionRepository {

    private static final PersonSectionsRepository personSectionsRepository = PersonSectionsRepositoryImpl.getInstance();
    private static final PersonRepository personRepository = PersonRepositoryImpl.getInstance();
    private static PersonToSectionRepository instance;

    private PersonToSectionRepositoryImpl() {
    }

    public static synchronized PersonToSectionRepository getInstance() {
        if (instance == null) {
            instance = new PersonToSectionRepositoryImpl();
        }
        return instance;
    }

    public PersonToSection save(PersonToSection personToSection) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, personToSection.getPersonId());
            preparedStatement.setLong(2, personToSection.getSectionId());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                personToSection = new PersonToSection(
                        resultSet.getLong("persons_sections_id"),
                        personToSection.getPersonId(),
                        personToSection.getSectionId()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personToSection;
    }

    public void update(PersonToSection personToSection) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setLong(1, personToSection.getPersonId());
            preparedStatement.setLong(2, personToSection.getSectionId());
            preparedStatement.setLong(3, personToSection.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteById(Long id) {
        boolean deleteResult = false;
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
    public boolean deleteByPersonId(Long id) {
        boolean deleteResult = false;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_USERID_SQL)) {

            preparedStatement.setLong(1, id);

            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteResult;
    }

    @Override
    public boolean deleteBySectionId(Long id) {
        boolean deleteResult = false;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_DEPARTMENT_ID_SQL)) {

            preparedStatement.setLong(1, id);

            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteResult;
    }

    @Override
    public List<PersonToSection> findAllByPersonId(Long personId) {
        List<PersonToSection> userToDepartmentList = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USERID_SQL)) {

            preparedStatement.setLong(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userToDepartmentList.add(createPersonToSection(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userToDepartmentList;
    }

    private PersonToSection createPersonToSection(ResultSet resultSet) throws SQLException {
        PersonToSection personToSection;
        personToSection = new PersonToSection(
                resultSet.getLong("persons_sections_id"),
                resultSet.getLong("person_id"),
                resultSet.getLong("section_id")
        );
        return personToSection;
    }

    @Override
    public List<PersonSection> findSectionByPersonId(Long personId) {
        List<PersonSection> personSectionList = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USERID_SQL)) {

            preparedStatement.setLong(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long sectionId = resultSet.getLong("id");
                Optional<PersonSection> optionalSection = personSectionsRepository.findById(sectionId);
                if (optionalSection.isPresent()) {
                    personSectionList.add(optionalSection.get());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personSectionList;
    }

    @Override
    public List<PersonToSection> findAllBySectionId(Long sectionId) {
        List<PersonToSection> personToSectionList = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_DEPARTMENT_ID_SQL)) {

            preparedStatement.setLong(1, sectionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                personToSectionList.add(createPersonToSection(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personToSectionList;
    }

    @Override
    public List<Person> findPersonsBySectionId(Long sectionId) {
        List<Person> personList = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_DEPARTMENT_ID_SQL)) {

            preparedStatement.setLong(1, sectionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long personId = resultSet.getLong("id");
                Optional<Person> optionalUser = personRepository.findById(personId);
                if (optionalUser.isPresent()) {
                    personList.add(optionalUser.get());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    @Override
    public Optional<PersonToSection> findByPersonIdAndSectiontId(Long personId, Long sectionId) {
        Optional<PersonToSection> personToSection = Optional.empty();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USERID_AND_DEPARTMENT_ID_SQL)) {

            preparedStatement.setLong(1, personId);
            preparedStatement.setLong(2, sectionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                personToSection = Optional.of(createPersonToSection(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personToSection;
    }
}
