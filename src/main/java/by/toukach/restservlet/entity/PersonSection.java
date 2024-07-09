package by.toukach.restservlet.entity;

import by.toukach.restservlet.repository.PersonToSectionRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonToSectionRepositoryImpl;

public class PersonSection {

    private static final PersonToSectionRepository personToSectionRepository =
            PersonToSectionRepositoryImpl.getInstance();

    Integer sectionId;
    String sectionName;

    public PersonSection() {
    }

    public PersonSection(Integer sectionId, String sectionName) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
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
}


