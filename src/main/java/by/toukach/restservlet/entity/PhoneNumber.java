package by.toukach.restservlet.entity;

public class PhoneNumber extends AbstractEntity {

    private Long id;
    private String number;
    private Person person;

    public PhoneNumber() {
    }

    public PhoneNumber(Long id, String number, Person person) {
        this.id = id;
        this.number = number;
        this.person = person;
    }

    public Long getId() {
        return id;
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

    @Override
    public String toString() {
        return "PhoneNumber{" +
               "id=" + id +
               ", number='" + number + '\'' +
               ", person=" + person +
               '}';
    }
}
