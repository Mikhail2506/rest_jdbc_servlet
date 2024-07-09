package by.toukach.restservlet.entity;

import by.toukach.restservlet.repository.PersonRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonRepositoryImpl;

public class PhoneNumber {

    private static final PersonRepository personRepository = PersonRepositoryImpl.getInstance();
    private Integer phoneNumberId;
    private String number;

    public PhoneNumber() {
    }

    public PhoneNumber(Integer phoneNumberId, String number) {
        this.phoneNumberId = phoneNumberId;
        this.number = number;
    }

    public Integer getPhoneNumberId() {
        return phoneNumberId;
    }

    public void setPhoneNumberId(Integer phoneNumberId) {
        this.phoneNumberId = phoneNumberId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
