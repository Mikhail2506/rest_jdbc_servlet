package by.toukach.restservlet.dto;

public class PersonSectionDTO {

    private String personSectionDTOName;

    public PersonSectionDTO() {

    }

    public PersonSectionDTO(
            String personSectionDTOName) {
        this.personSectionDTOName = personSectionDTOName;
    }

    public String getPersonSectionDTOName() {
        return personSectionDTOName;
    }

    public void setPersonSectionDTOName(String personSectionDTOName) {
        this.personSectionDTOName = personSectionDTOName;
    }

    @Override
    public String toString() {
        return "PersonSectionDTO{" +
                "personSectionDTOName='" + personSectionDTOName + '\'' +
                '}';
    }
}
