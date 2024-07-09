package by.toukach.restservlet.dto;

public class PersonSectionDTO {

   // private Integer personSectionDTOId;

    private String personSectionDTOName;

    public PersonSectionDTO() {

    }

    public PersonSectionDTO(
           // Integer personSectionDTOId,
            String personSectionDTOName) {

        //this.personSectionDTOId = personSectionDTOId;
        this.personSectionDTOName = personSectionDTOName;
    }

//    public Integer getPersonSectionDTOId() {
//        return personSectionDTOId;
//    }
//
//    public void setPersonSectionDTOId(Integer personSectionDTOId) {
//        this.personSectionDTOId = personSectionDTOId;
//    }

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
