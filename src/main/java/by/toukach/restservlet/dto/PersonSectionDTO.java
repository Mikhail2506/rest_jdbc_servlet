package by.toukach.restservlet.dto;

import java.util.List;

public class PersonSectionDTO {

    private String sectionName;

    private List<PersonDTO> personDTOList;

    public PersonSectionDTO() {

    }

    public PersonSectionDTO(String sectionName, List<PersonDTO> personDTOList) {

        this.sectionName = sectionName;
        this.personDTOList = personDTOList;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<PersonDTO> getPersonList() {
        return personDTOList;
    }

    public void setPersonList(List<PersonDTO> personDTOList) {
        this.personDTOList = personDTOList;
    }
}
