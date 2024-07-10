package by.toukach.restservlet.dto;

public class PhoneNumberDTO {

    private String number;

    public PhoneNumberDTO() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneNumberDTO(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneNumberDTO{" +
                "number='" + number + '\'' +
                '}';
    }
}
