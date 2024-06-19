package by.toukach.restservlet.service;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface PersonService {

    by.toukach.restservlet.entity.Person addPerson(PersonDTO personDTO);

    List<PersonDTO> readPersons()throws SQLException;

   PersonDTO readPerson(Long personId) throws SQLException;

    void deletePerson(Long PersonId);

    void updatePerson(PersonDTO personDTO) throws NotFoundException;
}
