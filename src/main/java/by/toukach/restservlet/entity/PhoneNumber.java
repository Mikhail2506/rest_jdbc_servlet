package by.toukach.restservlet.entity;

public class PhoneNumber extends AbstractEntity {

    private Long phoneNumberId;
    private String number;
    private Person person;

    public PhoneNumber() {
    }

    public PhoneNumber(Long phoneNumberId, String number, Person person) {
        this.phoneNumberId = phoneNumberId;
        this.number = number;
        this.person = person;
    }

    public Long getPhoneNumberId() {
        return phoneNumberId;
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
