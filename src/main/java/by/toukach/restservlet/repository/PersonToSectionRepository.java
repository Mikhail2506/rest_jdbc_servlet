package by.toukach.restservlet.repository;

import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PersonToSection;

import java.util.List;
import java.util.Optional;

public interface PersonToSectionRepository {

    void deleteByPersonId(int id);

    boolean deleteBySectionId(int id);

    List<PersonToSection> findAllByPersonId(int personId);

    List<PersonToSection> findAll();

    Optional<PersonToSection> findByPersonIdAndSectiontId(int personId, int sectionId);

    PersonToSection save(int personId, int sectionId);

    boolean exitsById(int personId);
}
