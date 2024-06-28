package by.toukach.restservlet.entity;

import by.toukach.restservlet.repository.PersonToSectionRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonToSectionRepositoryImpl;

import java.util.List;


public class PersonSection {

    private static final PersonToSectionRepository personToSectionRepository =
            PersonToSectionRepositoryImpl.getInstance();

    Integer sectionId;
    String sectionName;
    List<Person> personList;

    public PersonSection() {
    }

    public PersonSection(Integer sectionId, String sectionName, List<Person> personList) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.personList = personList;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<Person> getPersonList() {
        if (personList == null) {
            List<Person> personList = personToSectionRepository.findPersonsBySectionId(this.sectionId);

        }
        return personList;
    }

    public void setPersons(List<Person> personList) {

        this.personList = personList;
    }
}


