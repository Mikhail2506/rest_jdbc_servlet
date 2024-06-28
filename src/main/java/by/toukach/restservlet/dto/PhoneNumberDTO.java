package by.toukach.restservlet.dto;

public class PhoneNumberDTO {

    private Integer id;

    private String number;

    private PersonDTO personDTO;

    public PhoneNumberDTO(){
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setPersonDTO(PersonDTO personDTO) {
        this.personDTO = personDTO;
    }

    public PhoneNumberDTO(Integer id, String number, PersonDTO personDTO) {
        this.id = id;
        this.number = number;
        this.personDTO = personDTO;
    }

}
