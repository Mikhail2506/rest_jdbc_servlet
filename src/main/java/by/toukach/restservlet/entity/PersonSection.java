package by.toukach.restservlet.entity;

import by.toukach.restservlet.repository.PersonSectionsRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonSectionsRepositoryImpl;

import java.util.List;

public class PersonSection extends AbstractEntity {

    private static final PersonSectionsRepository personSectionsRepositoryImpl =
            PersonSectionsRepositoryImpl.getInstance();

    Long sectionId;
    String name;
    List<Person> personsList;

    public PersonSection() {
    }

    public PersonSection(Long sectionId, String name, List<Person> personsList) {
        this.sectionId = sectionId;
        this.name = name;
        this.personsList = personsList;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersonsList() {
        if (personsList == null) {
            this.personsList = personSectionsRepositoryImpl.findPersonsBySectionId(this.sectionId);
        }
        return personsList;
    }

    public void setPersons(List<Person> personsList) {

        this.personsList = personsList;
    }
}


