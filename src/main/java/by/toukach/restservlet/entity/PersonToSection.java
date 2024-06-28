package by.toukach.restservlet.entity;

public class PersonToSection {

    private Integer id;
    private Integer personId;
    private Integer sectionId;

    public PersonToSection() {
    }

    public PersonToSection(Integer id, Integer personId, Integer sectionId) {
        this.id = id;
        this.personId = personId;
        this.sectionId = sectionId;
    }

    public int getId() {
        return id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}

