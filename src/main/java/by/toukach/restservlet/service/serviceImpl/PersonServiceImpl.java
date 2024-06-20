package by.toukach.restservlet.service.serviceImpl;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.exception.NotFoundException;
import by.toukach.restservlet.mapper.PersonMapper;
import by.toukach.restservlet.mapper.impl.PersonMapperImpl;
import by.toukach.restservlet.repository.PersonRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonRepositoryImpl;
import by.toukach.restservlet.service.PersonService;

import java.sql.SQLException;
import java.util.List;

public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository = PersonRepositoryImpl.getInstance();
    private static final PersonMapper personMapper = PersonMapperImpl.getInstance();

    private static PersonService instance;

    private PersonServiceImpl() {
    }

    public static synchronized PersonService getInstance() {
        if (instance == null) {
            instance = new PersonServiceImpl();
        }
        return instance;
    }

    @Override
    public by.toukach.restservlet.entity.Person addPerson(PersonDTO personDTO) {
        by.toukach.restservlet.entity.Person person = personRepository.save(personMapper.map(personDTO));
        return person;
    }

    @Override
    public List<PersonDTO> readPersons() throws SQLException {
        List<Person> all = personRepository.findAll();
        return personMapper.map(all);
    }

    @Override
    public PersonDTO readPerson(Long id) throws SQLException {
        try {
            checkExistPerson(id);
        } catch (NotFoundException e) {
            System.out.println("Person with id " + id + " does not exist in DB!!!");
        }
        by.toukach.restservlet.entity.Person person = personRepository.findById(id).orElseThrow();
        return personMapper.map(person);
    }

    @Override
    public void deletePerson(Long id) {
        try {
            checkExistPerson(id);
        } catch (NotFoundException e) {
            System.out.println("Person with id " + id + " does not exist in DB!!!");
        }
        personRepository.deleteById(id);
    }

    @Override
    public void updatePerson(PersonDTO personDTO) throws NotFoundException {
        if (personDTO == null || personDTO.getPersonId() == null) {
            throw new NotFoundException("Person with name " + personDTO.getPersonName() + " does not exist in Data Base!");
        }
        try {
            checkExistPerson(personDTO.getPersonId());
        } catch (NotFoundException e) {
            System.out.println("Person with id " + personDTO.getPersonId() + " does not exist in DB!!!");
        }
        personRepository.update(personMapper.map(personDTO));
    }

    private void checkExistPerson(Long personId) throws NotFoundException {
        if (!personRepository.existById(personId)) {
            throw new NotFoundException("User not found.");
        }
    }
}
