package by.toukach.restservlet.repository;

import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PersonToSection;

import java.util.List;
import java.util.Optional;

public interface PersonSectionsRepository{

    PersonSection save(PersonSection personSection);

    void update(PersonSection personSection);

    boolean deleteById(int personSectionId);

    boolean exitsById(int personSectionId);

    Optional<PersonSection> findById(int personSectionId);

    //PersonSection save(PersonToSection personToSection);

   // List<PersonSection> findAllBYPersonId(Long personId);

    List<PersonSection> findAllBYPersonId(int personId);

    List<PersonSection> findAll();
}
