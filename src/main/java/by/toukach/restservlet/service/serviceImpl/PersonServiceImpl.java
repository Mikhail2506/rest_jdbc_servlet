package by.toukach.restservlet.service.serviceImpl;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.dto.PersonToUpdateDTO;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.exception.NotFoundException;
import by.toukach.restservlet.mapper.MapperPerson;
import by.toukach.restservlet.mapper.MapperPersonSection;
import by.toukach.restservlet.mapper.MapperPersonToUpdate;
import by.toukach.restservlet.mapper.PersonToUpdateDTOMapper;
import by.toukach.restservlet.repository.PersonRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonRepositoryImpl;
import by.toukach.restservlet.service.PersonService;

import java.sql.SQLException;
import java.util.List;

public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository = PersonRepositoryImpl.getInstance();
    private final MapperPerson mapperPerson = MapperPerson.INSTANCE;
    private final MapperPersonToUpdate mapperPersonToUpdate = PersonToUpdateDTOMapper.INSTANCE;
    private final MapperPersonSection mapperSection = MapperPersonSection.INSTANCE;

    @Override
    public PersonDTO addPerson(PersonDTO personDTO) {
        Person person = personRepository.save(mapperPerson.dtoToEntity(personDTO));
        return personDTO;
    }

    @Override
    public List<PersonDTO> readPersons() throws SQLException {
        List<Person> all = personRepository.findAll();
        return mapperPerson.entityToDtoList(all);
    }

    @Override
    public Person readPerson(Long id) throws SQLException {
        try {
            checkExistPerson(id);
        } catch (NotFoundException e) {
            System.out.println("Person with id " + id + " does not exist in DB!!!");
        }
        Person person = personRepository.findById(id).orElseThrow();
        //return mapperPerson.entityToDto(person);
        return person;
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
    public void updatePerson(PersonToUpdateDTO personDtoToUpdate) {
        if (personDtoToUpdate == null || personDtoToUpdate.getId() == null) {
            throw new IllegalArgumentException();
        }
        try {
            checkExistPerson(personDtoToUpdate.getId());
        } catch (NotFoundException e) {
            System.out.println("Person with id " + personDtoToUpdate.getId() + " does not exist in DB!!!");
        }
        personRepository.update(mapperPersonToUpdate.dtoToUpdateToEntity(personDtoToUpdate));
    }

    private void checkExistPerson(Long personId) throws NotFoundException {
        if (!personRepository.existById(personId)) {
            throw new NotFoundException("User not found.");
        }
    }
}
