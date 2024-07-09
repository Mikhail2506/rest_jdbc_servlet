package by.toukach.restservlet.repository;

import by.toukach.restservlet.entity.PersonSection;

import java.util.List;
import java.util.Optional;

public interface PersonSectionsRepository {

    PersonSection save(PersonSection personSection);

    void update(PersonSection personSection);

    boolean deleteById(int personSectionId);

    boolean exitsById(int personSectionId);

    Optional<PersonSection> findById(int personSectionId);

    List<PersonSection> findAllBYPersonId(int personId);

    List<PersonSection> findAll();
}
