package by.toukach.restservlet.dto;

import by.toukach.restservlet.entity.Person;

public class PhoneNumberDTO extends AbstractDTO {

    private Long id;

    private String number;

    private Person person;

    public PhoneNumberDTO(Long id, String number, Person person) {
        this.id = id;
        this.number = number;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
