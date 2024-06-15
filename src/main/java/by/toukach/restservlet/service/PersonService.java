package by.toukach.restservlet.service;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.dto.PersonToUpdateDTO;
import by.toukach.restservlet.entity.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonService {

    PersonDTO addPerson(PersonDTO personDTO);

    List<PersonDTO> readPersons()throws SQLException;

   // PersonDTO readPerson(Long id) throws SQLException;
   Person readPerson(Long id) throws SQLException;

    void deletePerson(Long id);

    void updatePerson(PersonToUpdateDTO personToUpdateDTO);
}
