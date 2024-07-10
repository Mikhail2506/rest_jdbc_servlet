package by.toukach.restservlet.repository.repositoryImpl;

import by.toukach.restservlet.db.ConnectionManager;
import by.toukach.restservlet.db.ConnectionManagerImpl;
import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.dto.PersonSectionDTO;
import by.toukach.restservlet.dto.PhoneNumberDTO;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PhoneNumber;
import by.toukach.restservlet.repository.PersonRepository;


import java.sql.*;
import java.util.*;

import static by.toukach.restservlet.db.PersonsDataBaseQueries.*;

public class PersonRepositoryImpl implements PersonRepository {

    private static PersonRepository instance;
    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

    private PersonRepositoryImpl() {
    }

    public static synchronized PersonRepository getInstance() {
        if (instance == null) {
            instance = new PersonRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Person save(PersonDTO personToSave) {

        Person person = new Person();
        person.setPhoneNumbersList(new ArrayList<>());
        person.setPersonSection(new ArrayList<>());

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertPersonStatement = connection
                     .prepareStatement(SAVE_PERSON_SQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertPhoneStatement = connection
                     .prepareStatement(SAVE_PERSON_PHONE_NUMBER_SQL);
             PreparedStatement findSectionIdStatement = connection
                     .prepareStatement(FIND_SECTION_BY_NAME_SQL);
             PreparedStatement insertPersonSectionStatement = connection
                     .prepareStatement(SAVE_PERSON_TO_SECTION_SQL)) {

            insertPersonStatement.setString(1, personToSave.getPersonName());
            insertPersonStatement.setString(2, personToSave.getPersonSurname());
            insertPersonStatement.setInt(3, personToSave.getPersonAge());
            int affectedRows = insertPersonStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            Integer personId;
            try (ResultSet generatedKeys = insertPersonStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    personId = generatedKeys.getInt(1);

                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            for (PhoneNumberDTO phone : personToSave.getPhoneNumberDTOList()) {
                insertPhoneStatement.setString(1, String.valueOf(phone));
                insertPhoneStatement.setInt(2, personId);
                insertPhoneStatement.executeUpdate();
            }

            for (PersonSectionDTO section : personToSave.getPersonSectionDTOList()) {
                int sectionId = getSectionIdByName(connection, section.getPersonSectionDTOName());
                if (sectionId != -1) {
                    insertPersonSectionStatement.setInt(1, personId);
                    insertPersonSectionStatement.setInt(2, sectionId);
                    insertPersonSectionStatement.executeUpdate();
                } else {
                    System.out.println("Section not found: " + section.getPersonSectionDTOName());
                }
            }

            person.setPersonId(personId);
            person.setPersonName(personToSave.getPersonName());
            person.setPersonSurname(personToSave.getPersonSurname());
            person.setPersonAge(personToSave.getPersonAge());
            person.getPersonSectionList();
            person.getPersonSectionList();

            return person;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getSectionIdByName(Connection connection, String sectionName) {

        try {
            PreparedStatement findSectionIdStatement = connection.prepareStatement(FIND_SECTION_BY_NAME_SQL);
            findSectionIdStatement.setString(1, sectionName);
            ResultSet resultSet = findSectionIdStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void update(Person person) {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement personStatement = connection.
                     prepareStatement(UPDATE_PERSON_SQL);
             PreparedStatement phoneNumberStatement = connection.
                     prepareStatement(SAVE_PERSON_PHONE_NUMBER_SQL);
             PreparedStatement personSectionStatement = connection.
                     prepareStatement(DELETE_PERSON_FROM_SECTIONS_BY_PERSON_ID_SQL);
             PreparedStatement personSectionInsertStatement = connection.
                     prepareStatement(SAVE_PERSON_TO_SECTION_SQL)) {

            personStatement.setString(1, person.getPersonName());
            personStatement.setString(2, person.getPersonSurname());
            personStatement.setInt(3, person.getPersonAge());
            personStatement.setInt(4, person.getPersonId());
            personStatement.executeUpdate();

            deletePersonPhoneNumbers(connection, person.getPersonId());
            for (PhoneNumber phoneNumber : person.getPhoneNumbersList()) {
                phoneNumberStatement.setString(1, String.valueOf(phoneNumber));
                phoneNumberStatement.setInt(2, person.getPersonId());
                phoneNumberStatement.executeUpdate();
            }

            deletePersonSections(connection, person.getPersonId());
            for (PersonSection section : person.getPersonSectionList()) {
                int sectionId = getSectionIdByName(connection, section.getSectionName());
                if (sectionId != -1) {
                    personSectionInsertStatement.setInt(1, person.getPersonId());
                    personSectionInsertStatement.setInt(2, sectionId);
                    personSectionInsertStatement.executeUpdate();
                } else {
                    System.out.println("Section not found: " + section.getSectionName());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePersonPhoneNumbers(Connection connection, int personId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PHONE_NUMBERS_BY_PERSON_ID_SQL)) {
            statement.setInt(1, personId);
            statement.executeUpdate();
        }
    }

    private void deletePersonSections(Connection connection, int personId) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(DELETE_PERSON_FROM_SECTIONS_BY_PERSON_ID_SQL)) {
            statement.setInt(1, personId);
            statement.executeUpdate();
        }
    }

    @Override
    public void deletePersonById(int personId) {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement deletePersonFromSectionsStatement = connection
                     .prepareStatement(DELETE_PERSON_FROM_SECTIONS_BY_PERSON_ID_SQL);
             PreparedStatement deletePhoneNumbersStatement = connection
                     .prepareStatement(DELETE_PHONE_NUMBERS_BY_PERSON_ID_SQL);
             PreparedStatement deletePersonStatement = connection.prepareStatement(DELETE_PERSON_BY_ID_SQL)) {

            deletePersonFromSectionsStatement.setInt(1, personId);
            deletePersonFromSectionsStatement.executeUpdate();

            deletePhoneNumbersStatement.setInt(1, personId);
            deletePhoneNumbersStatement.executeUpdate();

            deletePersonStatement.setInt(1, personId);
            deletePersonStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Person findById(int id) {
        Person person = new Person();
        person.setPhoneNumbersList(new ArrayList<>());
        person.setPersonSection(new ArrayList<>());

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PERSON_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int personId = resultSet.getInt("id");
                if (person.getPersonId() == 0 || person.getPersonId() == personId) {
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    int age = resultSet.getInt("age");

                    person.setPersonId(personId);
                    person.setPersonName(name);
                    person.setPersonSurname(surname);
                    person.setPersonAge(age);
                }

                int phoneId = resultSet.getInt("phone_id");
                String phone = resultSet.getString("phone");
                if (phoneId > 0) {
                    PhoneNumber phoneNumber = person.getPhoneNumbersList()
                            .stream().filter(k -> k.getPhoneNumberId() == phoneId)
                            .findFirst().orElse(null);
                    if (phoneNumber == null) {
                        phoneNumber = new PhoneNumber(phoneId, phone);
                        person.getPhoneNumbersList().add(phoneNumber);
                    }
                }

                int sectionId = resultSet.getInt("section_id");
                String sectionName = resultSet.getString("section_name");

                if (sectionId > 0) {
                    PersonSection personSection = person.getPersonSectionList()
                            .stream().filter(x -> x.getSectionName().equals(sectionName))
                            .findFirst().orElse(null);
                    if (personSection == null) {
                        personSection = new PersonSection(sectionId, sectionName);
                        person.getPersonSectionList().add(personSection);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public List<Person> findAll() {

        List<Person> persons = new ArrayList<>();

        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                int phoneId = resultSet.getInt("phone_id");
                String phone = resultSet.getString("phone");
                int sectionId = resultSet.getInt("section_id");
                String sectionName = resultSet.getString("section_name");

                Person person = persons.stream()
                        .filter(p -> p.getPersonId() == id)
                        .findFirst().orElse(null);
                if (person == null) {
                    person = new Person();
                    person.setPersonId(id);
                    person.setPersonName(name);
                    person.setPersonSurname(surname);
                    person.setPersonAge(age);
                    person.setPhoneNumbersList(new ArrayList<>());
                    person.setPersonSection(new ArrayList<>());
                    persons.add(person);
                }

                if (phoneId > 0) {
                    PhoneNumber phoneNumber = person.getPhoneNumbersList().stream().filter(k -> k.getPhoneNumberId() == phoneId).findFirst().orElse(null);
                    if (phoneNumber == null) {
                        phoneNumber = new PhoneNumber(phoneId, phone);
                        person.getPhoneNumbersList().add(phoneNumber);
                    }
                }

                if (sectionName != null) {
                    PersonSection personSection = person.getPersonSectionList().stream().filter(x -> x.getSectionName().equals(sectionName)).findFirst().orElse(null);
                    if (personSection == null) {
                        personSection = new PersonSection(sectionId, sectionName);
                        person.getPersonSectionList().add(personSection);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    public boolean existById(int id) {
        boolean isExists = false;
        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(EXIST_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);

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





