package by.toukach.restservlet.dto;

public class PhoneNumberDTO {

    private String number;

    private PersonDTO personDTO;

    public PhoneNumberDTO(){
    }

    public PhoneNumberDTO(String number, PersonDTO personDTO) {
        this.number = number;
        this.personDTO = personDTO;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PersonDTO getPersonDTO() {
        return personDTO;
    }

    public void setPerson(PersonDTO personDTO) {
        this.personDTO = personDTO;
    }
}
