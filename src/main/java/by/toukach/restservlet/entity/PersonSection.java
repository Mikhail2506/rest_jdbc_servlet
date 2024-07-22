package by.toukach.restservlet.entity;

public class PersonSection {

  private Integer sectionId;
  private String sectionName;

  public PersonSection() {
  }

  public PersonSection(Integer sectionId, String sectionName) {
    this.sectionId = sectionId;
    this.sectionName = sectionName;
  }

  public Integer getSectionId() {
    return sectionId;
  }

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }
}


