package by.toukach.restservlet.dto;

import by.toukach.restservlet.entity.Person;

import java.util.List;

public class PersonSectionDTO extends AbstractDTO {

    //private Long id;

    private String section;

   // private List<Person> person;

    public String getSection() {
        return section;
    }

    public PersonSectionDTO(Long id, String section, List<Person> person) {
       // this.id = id;
        this.section = section;
        //this.person = person;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public void setSection(String section) {
        this.section = section;
    }

//    public List<Person> getPerson() {
//        return person;
//    }
//
//    public void setPerson(List<Person> person) {
//        this.person = person;
//    }


    @Override
    public String toString() {
        return "PersonSectionDTO{" +
              // "id=" + id +
               ", section='" + section + '\'' +
              // ", person=" + person +
               '}';
    }
}
