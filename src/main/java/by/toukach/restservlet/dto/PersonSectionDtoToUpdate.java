package by.toukach.restservlet.dto;

import by.toukach.restservlet.entity.Person;

import java.util.List;

public class PersonSectionDtoToUpdate extends AbstractDTO{

    private String section;

    private List<Person> person;



    public PersonSectionDtoToUpdate(String section, List<Person> person) {
        this.section = section;
        this.person = person;
    }
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

    @Override
    public String toString() {
        return "PersonSectionDtoToUpdate{" +
               "section='" + section + '\'' +
               ", person=" + person +
               '}';
    }
}
