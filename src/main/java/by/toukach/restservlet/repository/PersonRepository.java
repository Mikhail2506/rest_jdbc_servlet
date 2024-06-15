package by.toukach.restservlet.repository;

import by.toukach.restservlet.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    Person save(Person person);

    void update(Person person);

    boolean deleteById(Long id);

    Optional<Person> findById(Long id);

    List<Person> findAll();

    boolean existById(Long id);

    Person createPerson(ResultSet resultSet) throws SQLException;
}
