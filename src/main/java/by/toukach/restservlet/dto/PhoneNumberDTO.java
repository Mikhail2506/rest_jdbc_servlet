package by.toukach.restservlet.dto;

import by.toukach.restservlet.entity.Person;

public class PhoneNumberDTO extends AbstractDTO {

    private String number;

    private Person person;

    public PhoneNumberDTO(String number, Person person) {
        this.number = number;
        this.person = person;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
