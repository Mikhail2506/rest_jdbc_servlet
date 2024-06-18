package by.toukach.restservlet.dto;

import by.toukach.restservlet.entity.Person;

import java.util.List;

public class PersonSectionDTO extends AbstractDTO {

    private String section;

    private List<PersonDTO> personDTOList;

    public PersonSectionDTO() {

    }

    public PersonSectionDTO(String section, List<PersonDTO> personDTOList) {

        this.section = section;
        this.personDTOList = personDTOList;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<PersonDTO> getPersonDTOList() {
        return personDTOList;
    }

    public void setPersonDTOList(List<PersonDTO> personDTOList) {
        this.personDTOList = personDTOList;
    }
}
