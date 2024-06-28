package by.toukach.restservlet.repository;

import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PersonSection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    Person save(Person person);

    void update(Person person);

    boolean deleteById(int id);

    Optional<Person> findById(int id);

    List<Person> findAll();

    boolean existById(int id);

    Person createPerson(ResultSet resultSet) throws SQLException;
}
