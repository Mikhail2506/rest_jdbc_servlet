package by.toukach.restservlet.entity;

import by.toukach.restservlet.repository.PersonSectionsRepository;
import by.toukach.restservlet.repository.PersonToSectionRepository;
import by.toukach.restservlet.repository.PhoneNumbersRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonSectionsRepositoryImpl;
import by.toukach.restservlet.repository.repositoryImpl.PersonToSectionRepositoryImpl;
import by.toukach.restservlet.repository.repositoryImpl.PhoneNumbersRepositoryImpl;

import java.util.List;

public class Person {

    private final PhoneNumbersRepository phoneNumbersRepository =
            PhoneNumbersRepositoryImpl.getInstance();
    private final PersonSectionsRepository personSectionsRepository =
            PersonSectionsRepositoryImpl.getInstance();
    private final PersonToSectionRepository personToSectionRepository =
            PersonToSectionRepositoryImpl.getInstance();

    private Long personId;

    private String personName;

    private String personSurname;

    private Long personAge;

    List<PhoneNumber> phoneNumbersList;

    List<PersonSection> personSectionList;

    public Person() {
    }

    public Person(Long personId, String personName, String personSurname, Long personAge,
                  List<PhoneNumber> phoneNumbersList, List<PersonSection> personSectionList) {
        super();
        this.personId = personId;
        this.personName = personName;
        this.personSurname = personSurname;
        this.personAge = personAge;
        this.phoneNumbersList = phoneNumbersList;
        this.personSectionList = personSectionList;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonSurname() {
        return personSurname;
    }

    public void setPersonSurname(String personSurname) {
        this.personSurname = personSurname;
    }

    public Long getPersonAge() {
        return personAge;
    }

    public void setPersonAge(Long personAge) {
        this.personAge = personAge;
    }

    public List<PhoneNumber> getPhoneNumbersList() {
        if (phoneNumbersList == null) {
            this.phoneNumbersList = phoneNumbersRepository.findAllByPersonId(this.personId);
        }
        return phoneNumbersList;
    }

    public void setPhoneNumbersList(List<PhoneNumber> phoneNumbersList) {
        this.phoneNumbersList = phoneNumbersList;
    }

    public List<PersonSection> getPersonSectionList() {
        if (personSectionList == null) {
            this.personSectionList = personToSectionRepository.findSectionByPersonId(this.personId);
        }
        return personSectionList;
    }

    public void setPersonSection(List<PersonSection> personSectionList) {
        this.personSectionList = personSectionList;
    }
}
