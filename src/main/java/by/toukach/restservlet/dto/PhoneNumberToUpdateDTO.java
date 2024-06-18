package by.toukach.restservlet.dto;

import by.toukach.restservlet.entity.Person;

public class PhoneNumberToUpdateDTO extends AbstractDTO {

    private Long phoneNumberId;
    private final String number;



    public PhoneNumberToUpdateDTO(String number) {

        this.number = number;

    }

    public String getNumber() {
        return number;
    }

}
