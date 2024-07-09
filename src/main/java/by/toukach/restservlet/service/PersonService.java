package by.toukach.restservlet.service;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface PersonService {

    PersonDTO addPerson(PersonDTO personDTOSave);

    List<PersonDTO> readPersons()throws SQLException;

   PersonDTO readPerson(int personId) throws SQLException;

    void deletePerson(int PersonId);

    void updatePerson(PersonDTO personDTO) throws NotFoundException;
}
