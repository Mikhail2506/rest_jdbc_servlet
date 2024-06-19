package by.toukach.restservlet.dto;

import java.util.List;

public class PersonDTO {

    private Long personId;

    private String personName;

    private String personSurname;

    private Long personAge;

    private List<PhoneNumberDTO> phoneNumberDTOList;

    private List<PersonSectionDTO> personSectionDTOList;

    public PersonDTO() {
    }

    public PersonDTO(Long personId, String personName, String personSurname, Long personAge,
                     List<PhoneNumberDTO> phoneNumberDTOList, List<PersonSectionDTO> personSectionDTOList) {
        this.personId = personId;
        this.personName = personName;
        this.personSurname = personSurname;
        this.personAge = personAge;
        this.phoneNumberDTOList = phoneNumberDTOList;
        this.personSectionDTOList = personSectionDTOList;

    }

    public Long getPersonId() {
        return personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonSurname() {
        return personSurname;
    }

    public void setPersonSurname(String personSurname) {
        this.personSurname = personSurname;
    }

    public Long getPersonAge() {
        return personAge;
    }

    public void setPersonAge(Long personAge) {
        this.personAge = personAge;
    }

    public List<PhoneNumberDTO> getPhoneNumberDTOList() {

        return phoneNumberDTOList;
    }

    public void setPhoneNumberDTOList(List<PhoneNumberDTO> phoneNumberDTOList) {

        this.phoneNumberDTOList = phoneNumberDTOList;
    }

    public List<PersonSectionDTO> getPersonSectionDTOList() {
        return personSectionDTOList;
    }

    public void setPersonSectionDTOList(List<PersonSectionDTO> personSectionDTOList) {
        this.personSectionDTOList = personSectionDTOList;
    }
}
