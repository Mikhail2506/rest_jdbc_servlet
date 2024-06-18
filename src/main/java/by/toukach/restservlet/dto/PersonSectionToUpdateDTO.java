package by.toukach.restservlet.dto;

import by.toukach.restservlet.entity.Person;

import java.util.List;

public class PersonSectionToUpdateDTO extends AbstractDTO{

    private final int personSectionId;

    private String section;

    private List<Person> person;

    public PersonSectionToUpdateDTO(int personSectionId, String section, List<Person> person) {
        this.personSectionId = personSectionId;
        this.section = section;
        this.person = person;
    }

    public int getPersonSectionId(){return personSectionId;}

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }

}
