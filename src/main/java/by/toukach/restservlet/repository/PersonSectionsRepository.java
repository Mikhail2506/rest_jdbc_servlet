package by.toukach.restservlet.repository;

import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PersonToSection;

import java.util.List;
import java.util.Optional;

public interface PersonSectionsRepository{

    PersonSection save(PersonSection personSection);

    void update(PersonSection personSection);

    boolean deleteById(Long id);

    List<PersonSection> findAllBYPersonId();

    boolean exitsById(Long id);

    List<Person> findPersonsBySectionId(Long id);

    Optional<PersonSection> findById(Long id);

    PersonToSection save(PersonToSection personToSection);

    List<PersonSection> findAllBYPersonId(Long id);
}
