package by.toukach.restservlet.repository.repositoryImpl;

import by.toukach.restservlet.db.ConnectionManager;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PersonToSection;
import by.toukach.restservlet.entity.PhoneNumber;
import by.toukach.restservlet.repository.PersonRepository;
import by.toukach.restservlet.repository.PersonSectionsRepository;
import by.toukach.restservlet.repository.PersonToSectionRepository;
import by.toukach.restservlet.repository.PhoneNumbersRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static by.toukach.restservlet.db.PersonsDataBaseQueries.*;

public class PersonRepositoryImpl implements PersonRepository {

    private static PersonRepository instance;
    private final PersonToSectionRepository  personToSectionRepository = PersonToSectionRepositoryImpl.getInstance();
    private final PhoneNumbersRepository phoneNumbersRepository = PhoneNumbersRepositoryImpl.getInstance();
    private final PersonSectionsRepository personSectionsRepository = PersonSectionsRepositoryImpl.getInstance();

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

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, person.getPersonName());
            preparedStatement.setString(2, person.getPersonSurname());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                person = new Person(
                        (long) resultSet.getLong("id"),
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
            person.getPersonSectionsList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    private void savePersonsSections(Person person) {
        if (person.getPersonSectionsList() != null && !person.getPersonSectionsList().isEmpty()) {
            List<Long> personSectionsIdList = new ArrayList<>(
                    person.getPersonSectionsList()
                            .stream()
                            .map(PersonSection::getSectionId)
                            .toList()
            );
            List<PersonSection> existsSectionList = personSectionsRepository.findAllBYPersonId();
            for (PersonSection per : existsSectionList) {
                if (!personSectionsIdList.contains(per.getSectionId())) {
                    personSectionsRepository.deleteById(person.getPersonId());
                }
                personSectionsIdList.remove(person.getPersonId());
            }
            for (Long sectionId : personSectionsIdList) {
                if (!personSectionsRepository.exitsById(person.getPersonId())) {
                    PersonToSection personToSection = new PersonToSection(
                            null,
                            person.getPersonId(),
                            sectionId);
                    personSectionsRepository.save(personToSection);
                }
            }
        } else {
            personSectionsRepository.deleteById(person.getPersonId());
        }
    }

    private void savePersonPhoneNumbers(Person person) {
        if (person.getPhoneNumbersList() != null && !person.getPhoneNumbersList().isEmpty()) {
            List<PhoneNumber> phoneNumberList = new ArrayList<>(person.getPhoneNumbersList());
            List<Long> existsPhoneNumberIdList = new ArrayList<>(
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

    private void saveOrUpdateExistNumber(PhoneNumber phoneNumber) {
        if (phoneNumbersRepository.exists(phoneNumber.getNumber())) {
            Optional<PhoneNumber> exitNumber = phoneNumbersRepository.findByNumber(phoneNumber.getNumber());
            if (exitNumber.isPresent()
                && exitNumber.get().getPerson() != null
                //&& exitNumber.get().getPerson().get() > 0)
            ) {
                phoneNumber = new PhoneNumber(exitNumber.get().getPhoneNumberId(),
                        exitNumber.get().getNumber(),
                        exitNumber.get().getPerson()
                );
                phoneNumbersRepository.update(phoneNumber);
            }
        } else {
            phoneNumbersRepository.save(phoneNumber);
        }
    }

    private void saveOrUpdateExitsSection(PersonSection personSection) {

    }

    private void savePhoneNumberList(Person person) {

        if (person.getPhoneNumbersList() != null && !person.getPhoneNumbersList().isEmpty()) {
            List<PhoneNumber> phoneNumberList = new ArrayList<>(person.getPhoneNumbersList());
            List<Long> existsPhoneNumberIdList = new ArrayList<>(
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
                    .forEach((Consumer<? super Long>) phoneNumbersRepository.findAllByPersonId(person.getPersonId()));
        } else {
            phoneNumbersRepository.deleteById(person.getPersonId());
        }
    }


    private void saveOrUpdateExitsNumber(PhoneNumber phoneNumber) {

        if (phoneNumbersRepository.exists(phoneNumber.getNumber())) {
            Optional<PhoneNumber> existNumbers = phoneNumbersRepository.findByNumber(phoneNumber.getNumber());
            if ((existNumbers != null)
                && (existNumbers.get().getPerson() != null)) {
                phoneNumber = new PhoneNumber(existNumbers.get().getPhoneNumberId(),
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
    public void update(Person person) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);) {

            preparedStatement.setString(1, person.getPersonName());
            preparedStatement.setString(2, person.getPersonSurname());
            preparedStatement.setLong(4, person.getPersonId());

            preparedStatement.executeUpdate();
            savePhoneNumberList(person);
            savePersonsSections(person);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteById(Long id) {
        boolean deleteResult = false;
        try (Connection connection = ConnectionManager.open();
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
    public Optional<Person> findById(Long id) {
        Person person = null;
        try (Connection connection = ConnectionManager.open();
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
        Long personId = resultSet.getLong("id");
        return new Person(
                personId,
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getInt("age"),
                null,
                null
        );
    }

    @Override
    public List<Person> findAll() {

        List<Person> persons = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                persons.add(save((Person) resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    public boolean existById(Long id) {
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





