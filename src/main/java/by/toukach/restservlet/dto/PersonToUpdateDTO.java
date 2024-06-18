package by.toukach.restservlet.dto;

import java.util.List;

public class PersonToUpdateDTO extends AbstractDTO {

    private Long personId;
    private String personName;
    private String personSurname;
    private int personAge;
    private List<PhoneNumberToUpdateDTO> phoneNumberToUpdateDTOs;
    private List<PersonSectionToUpdateDTO> personSectionToUpdateDTOs;

    public PersonToUpdateDTO(Long personId, String personName, String personSurname, Long personAge, List<PhoneNumberToUpdateDTO> phoneNumberToUpdateDTOs, List<PersonSectionDTO> map) {
    }

    public PersonToUpdateDTO(long personId, String personName, String personSurname,
                             int personAge,
                             List<PhoneNumberToUpdateDTO> phoneNumberToUpdateDTOs,
                             List<PersonSectionToUpdateDTO> personSectionToUpdateDTOs) {
        this.personId = personId;
        this.personName = personName;
        this.personSurname = personSurname;
        this.personAge = personAge;
        this.phoneNumberToUpdateDTOs = phoneNumberToUpdateDTOs;
        this.personSectionToUpdateDTOs = personSectionToUpdateDTOs;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getPersonName() {
        return personName;
    }

    public String getPersonSurname() {
        return personSurname;
    }

    public int getPersonAge() {
        return personAge;
    }

    public void setPersonAge(int personAge) {
        this.personAge = personAge;
    }

    public List<PhoneNumberToUpdateDTO> getPhoneNumberToUpdateDTOs() {
        return phoneNumberToUpdateDTOs;
    }

    public List<PersonSectionToUpdateDTO> getPersonSectionToUpdateDTOs() {
        return personSectionToUpdateDTOs;
    }
}
