package by.toukach.restservlet.repository;

import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PersonToSection;

import java.util.List;
import java.util.Optional;

public interface PersonSectionsRepository{

    PersonSection save(PersonSection personSection);

    void update(PersonSection personSection);

    boolean deleteById(Long personSectionId);

    boolean exitsById(Long personSectionId);

    Optional<PersonSection> findById(Long personSectionId);

    PersonToSection save(PersonToSection personToSection);

   // List<PersonSection> findAllBYPersonId(Long personId);

    List<PersonSection> findAll();
}
