package by.toukach.restservlet.dto;

import java.util.List;

public class PersonDTO {

  private Integer personId;

  private String personName;

  private String personSurname;

  private int personAge;

  private List<PhoneNumberDTO> phoneNumberDTOList;

  private List<PersonSectionDTO> personSectionDTOList;

  public PersonDTO() {
  }

  public PersonDTO(Integer personId,
      String personName, String personSurname, int personAge,
      List<PhoneNumberDTO> phoneNumberDTOList, List<PersonSectionDTO> personSectionDTOList) {
    this.personId = personId;
    this.personName = personName;
    this.personSurname = personSurname;
    this.personAge = personAge;
    this.phoneNumberDTOList = phoneNumberDTOList;
    this.personSectionDTOList = personSectionDTOList;

  }

  public PersonDTO(String personName, String personSurname, int personAge,
      List<PhoneNumberDTO> phoneNumberDTOList, List<PersonSectionDTO> personSectionDTOList) {
    this.personName = personName;
    this.personSurname = personSurname;
    this.personAge = personAge;
    this.phoneNumberDTOList = phoneNumberDTOList;
    this.personSectionDTOList = personSectionDTOList;

  }

  public Integer getPersonId() {
    return personId;
  }

  public String getPersonName() {
    return personName;
  }

  public void setPersonName(String personName) {
    this.personName = personName;
  }

  public String getPersonSurname() {
    return personSurname;
  }

  public void setPersonSurname(String personSurname) {
    this.personSurname = personSurname;
  }

  public int getPersonAge() {
    return personAge;
  }

  public void setPersonAge(int personAge) {
    this.personAge = personAge;
  }

  public List<PhoneNumberDTO> getPhoneNumberDTOList() {

    return phoneNumberDTOList;
  }

  public void setPhoneNumberDTOList(List<PhoneNumberDTO> phoneNumberDTOList) {

    this.phoneNumberDTOList = phoneNumberDTOList;
  }

  public List<PersonSectionDTO> getPersonSectionDTOList() {
    return personSectionDTOList;
  }

  public void setPersonSectionDTOList(List<PersonSectionDTO> personSectionDTOList) {
    this.personSectionDTOList = personSectionDTOList;
  }

  @Override
  public String toString() {
    return "PersonDTO{" +
        "personName='" + personName + '\'' +
        ", personSurname='" + personSurname + '\'' +
        ", personAge=" + personAge +
        ", phoneNumberDTOList=" + phoneNumberDTOList +
        ", personSectionDTOList=" + personSectionDTOList +
        '}';
  }
}
