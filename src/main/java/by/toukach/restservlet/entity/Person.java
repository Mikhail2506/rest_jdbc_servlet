package by.toukach.restservlet.entity;

import by.toukach.restservlet.repository.PersonSectionsRepository;
import by.toukach.restservlet.repository.PhoneNumbersRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonSectionsRepositoryImpl;
import by.toukach.restservlet.repository.repositoryImpl.PhoneNumbersRepositoryImpl;

import java.util.List;

public class Person extends AbstractEntity {

    private final PhoneNumbersRepository phoneNumbersRepository =  PhoneNumbersRepositoryImpl.getInstance();
    private final PersonSectionsRepository personSectionsRepository =  PersonSectionsRepositoryImpl.getInstance();

    private Long id;

    private String name;

    private String surname;

    private int age;

    List<PhoneNumber> phoneNumberList;

    List<PersonSection> personsSectionsList;

    public Person() {
    }

    public Person(String name, String surname, int age) {
        super();
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Person(Long id, String name, String surname, int age,
                  List<PhoneNumber> phoneNumberList, List<PersonSection> personsSectionsList) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phoneNumberList = phoneNumberList;
        this.personsSectionsList = personsSectionsList;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<PhoneNumber> getPhoneNumberList() {
        if (phoneNumberList == null) {
            this.phoneNumberList = phoneNumbersRepository.findAll(this.id);
        }
        return phoneNumberList;
    }

    public void setPhoneNumberList(List<PhoneNumber> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }

    public List<PersonSection> getPersonsSectionsList() {
        if (personsSectionsList == null) {
//            this.personsSectionsList = personSectionsRepository.findAll(this.getId());
            this.personsSectionsList = personSectionsRepository.findAll();
        }
        return personsSectionsList;
    }

    public void setPersonsSections(List<PersonSection> personsSectionsList) {
        this.personsSectionsList = personsSectionsList;
    }

    @Override
    public String toString() {
        return "Person{" +
               "phoneNumbersRepository=" + phoneNumbersRepository +
               ", personSectionsRepository=" + personSectionsRepository +
               ", id=" + id +
               ", name='" + name + '\'' +
               ", surname='" + surname + '\'' +
               ", age=" + age +
               ", phoneNumberList=" + phoneNumberList +
               ", personsSectionsList=" + personsSectionsList +
               '}';
    }
}
