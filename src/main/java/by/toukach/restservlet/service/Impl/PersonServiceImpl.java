package by.toukach.restservlet.service.Impl;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.exception.NotFoundException;
import by.toukach.restservlet.mapper.PersonMapper;
import by.toukach.restservlet.mapper.impl.PersonMapperImpl;
import by.toukach.restservlet.repository.PersonRepository;
import by.toukach.restservlet.repository.Impl.PersonRepositoryImpl;
import by.toukach.restservlet.service.PersonService;

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
  public PersonDTO addPerson(PersonDTO personDTOSave) {
    Person person = personRepository.save(personDTOSave);
    return personMapper.map(personRepository.findById(person.getPersonId()).orElse(person));
  }

  @Override
  public List<PersonDTO> readPersons() {
    List<Person> all = personRepository.findAll();
    return personMapper.map(all);
  }

  @Override
  public PersonDTO readPerson(int id) throws NotFoundException {
    checkExistPerson(id);
    Person person = personRepository.findById(id).orElseThrow();
    return personMapper.map(person);
  }

  @Override
  public void deletePerson(int personId) {
    try {
      checkExistPerson(personId);
    } catch (NotFoundException e) {
      System.out.println("Person with id " + personId + " does not exist in DB!!!");
    }
    personRepository.deletePersonById(personId);
  }

  @Override
  public void updatePerson(PersonDTO personDTO) throws NotFoundException {
    if ((personDTO == null) || (personDTO.getPersonId() == null)) {
      throw new NotFoundException(
          "Person with name " + personDTO.getPersonName() + " does not exist in Data Base!");
    }
    try {
      checkExistPerson(personDTO.getPersonId());
    } catch (NotFoundException e) {
      System.out.println("Person with id " + personDTO.getPersonId() + " does not exist in DB!!!");
    }
    personRepository.update(personMapper.map(personDTO));
  }

  private void checkExistPerson(int personId) throws NotFoundException {
    if (!personRepository.existById(personId)) {
      throw new NotFoundException("User not found.");
    }
  }
}
