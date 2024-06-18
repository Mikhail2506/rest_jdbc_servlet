package by.toukach.restservlet.entity;

import by.toukach.restservlet.repository.PersonSectionsRepository;
import by.toukach.restservlet.repository.PhoneNumbersRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonSectionsRepositoryImpl;
import by.toukach.restservlet.repository.repositoryImpl.PhoneNumbersRepositoryImpl;

import java.util.List;

public class Person extends AbstractEntity {

    private final PhoneNumbersRepository phoneNumbersRepository =
            PhoneNumbersRepositoryImpl.getInstance();
    private final PersonSectionsRepository personSectionsRepository =
            PersonSectionsRepositoryImpl.getInstance();

    private Long personId;

    private String personName;

    private String personSurname;

    private Long personAge;

    List<PhoneNumber> phoneNumbersList;

    List<PersonSection> personSectionsList;

    public Person() {
    }

//    public Person(String name, String surname, int age) {
//        super();
//        this.name = name;
//        this.surname = surname;
//        this.age = age;
//    }

    public Person(Long personId, String personName, String personSurname, Long personAge,
                  List<PhoneNumber> phoneNumbersList, List<PersonSection> personSectionsList) {
        super();
        this.personId = personId;
        this.personName = personName;
        this.personSurname = personSurname;
        this.personAge = personAge;
        this.phoneNumbersList = phoneNumbersList;
        this.personSectionsList = personSectionsList;
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

    public List<PersonSection> getPersonSectionsList() {
        if (personSectionsList == null) {
            this.personSectionsList = personSectionsRepository.findAllBYPersonId(this.personId);

        }
        return personSectionsList;
    }

    public void setPersonsSections(List<PersonSection> personsSectionsList) {
        this.personSectionsList = personsSectionsList;
    }


}
