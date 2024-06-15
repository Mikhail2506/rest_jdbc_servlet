package by.toukach.restservlet.entity;

import by.toukach.restservlet.repository.PersonSectionsRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonSectionsRepositoryImpl;

import java.util.List;

public class PersonSection extends AbstractEntity {

    private final PersonSectionsRepository personSectionsRepositoryImpl =  PersonSectionsRepositoryImpl.getInstance();

    Long id;
    String name;
    List<Person> persons;

    public PersonSection() {
    }

    public PersonSection(Long id, String name, List<Person> persons) {
        this.id = id;
        this.name = name;
        this.persons = persons;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersons() {
        if (persons == null) {
            this.persons = personSectionsRepositoryImpl.findPersonsBySectionId(this.id);
        }
        return persons;
    }

    public void setPersons(List<Person> persons) {

        this.persons = persons;
    }
}


