package by.toukach.restservlet.dto;

import java.util.List;

public class PersonToUpdateDTO extends AbstractDTO {

    private Long id;
    private String name;
    private String surname;
    private List<PhoneNumberToUpdateDTO> phoneNumberToUpdateDTOs;
    private List<PersonSectionDtoToUpdate> personSectionToUpdateDTOs;

    public PersonToUpdateDTO() {
    }

    public PersonToUpdateDTO(Long id, String name, String surname,
                             List<PhoneNumberToUpdateDTO> phoneNumberToUpdateDTOs,
                             List<PersonSectionDtoToUpdate> personSectionToUpdateDTOs) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumberToUpdateDTOs = phoneNumberToUpdateDTOs;
        this.personSectionToUpdateDTOs = personSectionToUpdateDTOs;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<PhoneNumberToUpdateDTO> getPhoneNumberToUpdateDTOs() {
        return phoneNumberToUpdateDTOs;
    }

    public List<PersonSectionDtoToUpdate> getPersonSectionToUpdateDTOs() {
        return personSectionToUpdateDTOs;
    }

    @Override
    public String toString() {
        return "PersonToUpdateDTO{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", surname='" + surname + '\'' +
               ", phoneNumberToUpdateDTOs=" + phoneNumberToUpdateDTOs +
               ", personSectionToUpdateDTOs=" + personSectionToUpdateDTOs +
               '}';
    }
}
