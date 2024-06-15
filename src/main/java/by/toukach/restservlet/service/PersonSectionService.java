package by.toukach.restservlet.service;

import by.toukach.restservlet.dto.PersonSectionDTO;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.exception.NotFoundException;

import java.util.List;

public interface PersonSectionService {

    PersonSection save(PersonSectionDTO personSectionDTO) throws NotFoundException;

    void update(PersonSectionDTO personSectionDTO) throws NotFoundException;

    PersonSectionDTO findById(Long personSectionId) throws NotFoundException;

    List<PersonSectionDTO> findAll();

    void delete(Long personSectionId) throws NotFoundException;

    void deletePersonFromSection(Long personSectionId, Long personId) throws NotFoundException;

    void addPersonToPersonSection(Long personSectionId, Long personId) throws NotFoundException;

}
