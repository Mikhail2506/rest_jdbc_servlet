package by.toukach.restservlet.service;

import by.toukach.restservlet.dto.PersonSectionDTO;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.exception.NotFoundException;

import java.util.List;

public interface PersonSectionService {

    PersonSection save(PersonSectionDTO personSectionDTO) throws NotFoundException;

    void update(PersonSectionDTO personSectionDTO) throws NotFoundException;

    PersonSectionDTO findById(int personSectionId) throws NotFoundException;

    List<PersonSectionDTO> findAll();

    void delete(int personSectionId) throws NotFoundException;

    void deletePersonFromSection(int personSectionId, int personId) throws NotFoundException;

    void addPersonToPersonSection(int personSectionId, int personId) throws NotFoundException;

}
