package by.toukach.restservlet.repository;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PersonSection;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    Person save(PersonDTO person);

    void update(Person person);

    void deletePersonById(int personId);

    Person findById(int id);

    List<Person> findAll();

    boolean existById(int id);

}
