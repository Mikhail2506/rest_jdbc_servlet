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
    public Person save(Person person) {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, person.getPersonName());
            preparedStatement.setString(2, person.getPersonSurname());
            preparedStatement.setLong(3, person.getPersonAge());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                person = new Person(
                        resultSet.getInt("id"),
                        person.getPersonName(),
                        person.getPersonSurname(),
                        person.getPersonAge(),
                        null,
                        null
                );
            }

            savePersonPhoneNumbers(person);
            savePersonsSections(person);
            person.getPhoneNumbersList();
            person.getPersonSectionList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public void update(Person person) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

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
            List<Integer> personSectionIdList = new ArrayList<>(
                    person.getPersonSectionList()
                            .stream()
                            .map(PersonSection::getSectionId)
                            .toList()
            );

            List<PersonToSection> existsSectionList = personToSectionRepository.findAllByPersonId(person.getPersonId());
            for (PersonToSection per : existsSectionList) {
                if (!personSectionIdList.contains(per.getSectionId())) {
                    personSectionsRepository.deleteById(person.getPersonId());
                }
                personSectionIdList.remove(person.getPersonId());
            }
            for (Integer sectionId : personSectionIdList) {
                if (personSectionsRepository.exitsById(person.getPersonId())) {
                    PersonToSection personToSection = new PersonToSection(
                            null,
                            person.getPersonId(),
                            sectionId);
                    personToSectionRepository.save(personToSection);
                }
            }
        } else {
            personToSectionRepository.deleteByPersonId(person.getPersonId());
        }
    }

    private void savePersonPhoneNumbers(Person person) {
        if (person.getPhoneNumbersList() != null && !person.getPhoneNumbersList().isEmpty()) {
            List<PhoneNumber> phoneNumberList = new ArrayList<>(person.getPhoneNumbersList());
            List<Integer> existsPhoneNumberIdList = new ArrayList<>(
                    phoneNumbersRepository.findAllByPersonId(person.getPersonId())
                            .stream()
                            .map(PhoneNumber::getPhoneNumberId)
                            .toList()
            );

            for (int i = 0; i < phoneNumberList.size(); i++) {
                PhoneNumber phoneNumber = phoneNumberList.get(i);
                phoneNumber.setPerson(person);
                if (existsPhoneNumberIdList.contains(phoneNumber.getPhoneNumberId())) {
                    phoneNumbersRepository.update(phoneNumber);
                } else {
                    saveOrUpdateExitsNumber(phoneNumber);
                }
                phoneNumberList.set(i, null);
                existsPhoneNumberIdList.remove(phoneNumber.getPhoneNumberId());
            }
            phoneNumberList
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(phoneNumber -> {
                        phoneNumber.setPerson(person);
                        phoneNumbersRepository.save(phoneNumber);
                    });
            existsPhoneNumberIdList
                    .stream()
                    .forEach(phoneNumbersRepository::deleteById);
        } else {
            phoneNumbersRepository.deleteById(person.getPersonId());
        }
    }

    private void saveOrUpdateExitsNumber(PhoneNumber phoneNumber) {

        if (phoneNumbersRepository.exists(phoneNumber.getNumber())) {
            Optional<PhoneNumber> existNumbers = phoneNumbersRepository.findByNumber(phoneNumber.getNumber());
            if ((existNumbers != null)
                    && (existNumbers.get().getPerson() != null)
            ) {
                phoneNumber = new PhoneNumber(
                        existNumbers.get().getPhoneNumberId(),
                        existNumbers.get().getNumber(),
                        existNumbers.get().getPerson()
                );
                phoneNumbersRepository.update(phoneNumber);
            }
        } else {
            phoneNumbersRepository.save(phoneNumber);
        }
    }

    @Override
    public boolean deleteById(int id) {
        boolean deleteResult = false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);) {

            personSectionsRepository.deleteById(id);
            phoneNumbersRepository.deleteById(id);

            preparedStatement.setLong(1, id);
            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteResult;
    }

    @Override
    public Optional<Person> findById(int id) {
         Person person = null;
        //person = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                person = createPerson(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(person);
    }

    public Person createPerson(ResultSet resultSet) throws SQLException {
        //Integer personId = resultSet.getInt("id");
        Person person = new Person();

//        return new Person(
//                personId,
//                resultSet.getString("name"),
//                resultSet.getString("surname"),
//                resultSet.getInt("age"),
//
//                null,
//                null
//        );

        int personId = resultSet.getInt("id");
        String personName = resultSet.getString("name");
        String personSurname = resultSet.getString("surname");
        int personAge = resultSet.getInt("age");
        int phoneId = resultSet.getInt("phone_id");
        String phone = resultSet.getString("phone");
        int sectionId = resultSet.getInt("section_id");
        String sectionName = resultSet.getString("section_name");

        person.setPersonId(personId);
        person.setPersonName(personName);
        person.setPersonSurname(personSurname);
        person.setPersonAge(personAge);


        person.setPhoneNumbersList(new ArrayList<>());
        person.setPersonSection(personSectionsRepository.findAllBYPersonId(personId));

        if (phoneId > 0) {
            PhoneNumber phoneObj = new PhoneNumber(phoneId, phone, person);
            person.getPhoneNumbersList().add(phoneObj);
        }

        return person;
    }

    @Override
    public List<Person> findAll() {

        List<Person> persons = new ArrayList<>();

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                persons.add(save(createPerson(resultSet)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                String surname = resultSet.getString("surname");
//                int age = resultSet.getInt("age");
//                int phoneId = resultSet.getInt("phone_id");
//                String phone = resultSet.getString("phone");
//                int sectionId = resultSet.getInt("section_id");
//                String sectionName = resultSet.getString("section_name");
//
//                Person person = persons.stream().filter(p -> p.getPersonId() == id).findFirst().orElse(null);
//                person.setPhoneNumbersList(new ArrayList<>());
//                if (person == null) {
//                    person = new Person();
//                    persons.add(person);
//                }
//                if (phoneId > 0) {
//                    PhoneNumber phoneNumber = new PhoneNumber(phoneId, phone, person);
//                    person.addPhone(phoneNumber);
//                }
//                if (sectionId > 0) {
//                    PersonRepository section = new PersonRepository(sectionId, sectionName);
//                    person.addSection(section);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return persons;

    }


    @Override
    public boolean existById(int id) {
        boolean isExists = false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EXIST_BY_ID_SQL)) {

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





