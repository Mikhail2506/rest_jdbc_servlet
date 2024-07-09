package by.toukach.restservlet.repository.repositoryImpl;

import by.toukach.restservlet.db.ConnectionManager;
import by.toukach.restservlet.db.ConnectionManagerImpl;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PersonToSection;
import by.toukach.restservlet.entity.PhoneNumber;
import by.toukach.restservlet.repository.PersonRepository;
import by.toukach.restservlet.repository.PersonSectionsRepository;
import by.toukach.restservlet.repository.PersonToSectionRepository;
import by.toukach.restservlet.repository.PhoneNumbersRepository;

import java.sql.*;
import java.util.*;

import static by.toukach.restservlet.db.PersonsDataBaseQueries.*;

public class PersonRepositoryImpl implements PersonRepository {

    private static PersonRepository instance;
    private final PersonToSectionRepository personToSectionRepository = PersonToSectionRepositoryImpl.getInstance();
    private final PhoneNumbersRepository phoneNumbersRepository = PhoneNumbersRepositoryImpl.getInstance();
    private final PersonSectionsRepository personSectionsRepository = PersonSectionsRepositoryImpl.getInstance();
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
    public Person save(Person personToSave) {

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

            // Получение ID вставленной персоны
            int personId;
            try (ResultSet generatedKeys = insertPersonStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    personId = generatedKeys.getInt("id");
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            // Вставка номеров телефона
            for (PhoneNumber phone : personToSave.getPhoneNumbersList()) {
                insertPhoneStatement.setString(1, String.valueOf(phone));
                insertPhoneStatement.setInt(2, personId);
                insertPhoneStatement.executeUpdate();
            }

            // Вставка связей персоны с секциями
            for (PersonSection section : personToSave.getPersonSectionList()) {
                int sectionId = getSectionIdByName(connection, section.getSectionName());
                if (sectionId != -1) {
                    insertPersonSectionStatement.setInt(1, personId);
                    insertPersonSectionStatement.setInt(2, sectionId);
                    insertPersonSectionStatement.executeUpdate();
                } else {
                    // Если секция не найдена, можно либо пропустить, либо вставить новую секцию
                    System.out.println("Section not found: " + section.getSectionName());
                }
            }

            // Создание объекта Person с полученными данными
            Person person = null;
            person.setPersonId(personId);
            person.setPersonName(personToSave.getPersonName());
            person.setPersonSurname(personToSave.getPersonSurname());
            person.setPersonAge(personToSave.getPersonAge());
            person.setPhoneNumbersList(personToSave.getPhoneNumbersList());
            person.setPersonSection(personToSave.getPersonSectionList());

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
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PERSON_SQL)) {

            preparedStatement.setString(1, person.getPersonName());
            preparedStatement.setString(2, person.getPersonSurname());
            preparedStatement.setLong(3, person.getPersonAge());

            preparedStatement.executeUpdate();
            savePersonPhoneNumbers(person);
            savePersonsSections(person);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void savePersonsSections(Person person) {
        if (person.getPersonSectionList() != null && !person.getPersonSectionList().isEmpty()) {
            List<Integer> personSectionIdList = new ArrayList<>(person.getPersonSectionList()
                    .stream().map(PersonSection::getSectionId).toList());

            List<PersonToSection> existsSectionList = personToSectionRepository
                    .findAllByPersonId(person.getPersonId());
            for (PersonToSection per : existsSectionList) {
                if (!personSectionIdList.contains(per.getSectionId())) {
                    personSectionsRepository.deleteById(person.getPersonId());
                }
                personSectionIdList.remove(person.getPersonId());
            }
            for (Integer sectionId : personSectionIdList) {
                if (personSectionsRepository.exitsById(person.getPersonId())) {
                    PersonToSection personToSection = new PersonToSection(null, person.getPersonId(), sectionId);
                    personToSectionRepository.save(person.getPersonId(), sectionId);
                }
            }
        } else {
            personToSectionRepository.deleteByPersonId(person.getPersonId());
        }
    }

    private void savePersonPhoneNumbers(Person person) {
        if (person.getPhoneNumbersList() != null && !person.getPhoneNumbersList().isEmpty()) {
            List<PhoneNumber> phoneNumberList = new ArrayList<>(person.getPhoneNumbersList());
            List<Integer> existsPhoneNumberIdList = new ArrayList<>(phoneNumbersRepository
                    .findAllByPersonId(person.getPersonId()).stream().map(PhoneNumber::getPhoneNumberId).toList());

            for (int i = 0; i < phoneNumberList.size(); i++) {
                PhoneNumber phoneNumber = phoneNumberList.get(i);
                if (existsPhoneNumberIdList.contains(phoneNumber.getPhoneNumberId())) {
                    phoneNumbersRepository.update(phoneNumber);
                } else {
                    saveOrUpdateExitsNumber(phoneNumber);
                }
                phoneNumberList.set(i, null);
                existsPhoneNumberIdList.remove(phoneNumber.getPhoneNumberId());
            }
            phoneNumberList.stream().filter(Objects::nonNull).forEach(phoneNumber -> {
                phoneNumbersRepository.save(phoneNumber);
            });
            existsPhoneNumberIdList.stream().forEach(phoneNumbersRepository::deleteById);
        } else {
            phoneNumbersRepository.deleteById(person.getPersonId());
        }
    }

    private void saveOrUpdateExitsNumber(PhoneNumber phoneNumber) {

        if (phoneNumbersRepository.exists(phoneNumber.getNumber())) {
            Optional<PhoneNumber> existNumbers = phoneNumbersRepository.findByNumber(phoneNumber.getNumber());
            if (existNumbers != null) {
                phoneNumber = new PhoneNumber(existNumbers.get().getPhoneNumberId(), existNumbers.get().getNumber());
                phoneNumbersRepository.update(phoneNumber);
            }
        } else {
            phoneNumbersRepository.save(phoneNumber);
        }
    }

    @Override
    public void deletePersonById(int personId) {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement deletePersonFromSectionsStatement = connection
                     .prepareStatement(DELETE_PERSON_FROM_SECTIONS_BY_PERSON_ID_SQL);
             PreparedStatement deletePhoneNumbersStatement = connection
                     .prepareStatement(DELETE_PHONE_NUMBERS_BY_PERSON_ID_SQL);
             PreparedStatement deletePersonStatement = connection.prepareStatement(DELETE_PERSON_BY_ID_SQL);) {

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





