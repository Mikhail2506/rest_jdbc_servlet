package by.toukach.restservlet.entity;

import by.toukach.restservlet.repository.PersonRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonRepositoryImpl;

public class PhoneNumber {

    private static final PersonRepository personRepository = PersonRepositoryImpl.getInstance();
    private int phoneNumberId;
    private String number;
    private Person person;

    public PhoneNumber() {
    }

    public PhoneNumber(Integer phoneNumberId, String number, Person person) {
        this.phoneNumberId = phoneNumberId;
        this.number = number;
        this.person = person;

    }

    public int getPhoneNumberId() {
        return phoneNumberId;
    }

    public void setPhoneNumberId(int phoneNumberId) {
        this.phoneNumberId = phoneNumberId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getPerson() {
        if (person != null && person.getPersonId() > 0 && person.getPersonName() == null) {
            this.person = personRepository.findById(person.getPersonId()).orElse(person);
        } else if (person != null && person.getPersonId() == 0) {
            this.person = null;
        }
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
