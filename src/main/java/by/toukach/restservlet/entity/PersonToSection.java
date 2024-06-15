package by.toukach.restservlet.entity;

public class PersonToSection {

    private Long id;
    private Long personId;
    private Long sectionId;

    public PersonToSection() {
    }

    public PersonToSection(Long id, Long personId, Long sectionId) {
        this.id = id;
        this.personId = personId;
        this.sectionId = sectionId;
    }

    public Long getId() {
        return id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }
}

