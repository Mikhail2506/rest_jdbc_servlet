package by.toukach.restservlet.repository;

import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PersonToSection;

import java.util.List;
import java.util.Optional;

public interface PersonToSectionRepository {

    boolean deleteByPersonId(Long id);

    boolean deleteBySectionId(Long id);

    List<PersonToSection> findAllByPersonId(Long personId);

    List<PersonSection> findSectionByPersonId(Long personId);

    List<PersonToSection> findAllBySectionId(Long sectionId);

    List<Person> findPersonsBySectionId(Long sectionId);

    Optional<PersonToSection> findByPersonIdAndSectiontId(Long personId, Long sectionId);

    PersonToSection save(PersonToSection personToSection);

    boolean exitsById(Long personId);
}
