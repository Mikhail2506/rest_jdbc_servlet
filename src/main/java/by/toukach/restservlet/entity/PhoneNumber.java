package by.toukach.restservlet.entity;

public class PhoneNumber {

  private Integer phoneNumberId;
  private String number;

  public PhoneNumber() {
  }

  public PhoneNumber(Integer phoneNumberId, String number) {
    this.phoneNumberId = phoneNumberId;
    this.number = number;
  }

  public Integer getPhoneNumberId() {
    return phoneNumberId;
  }

  public void setPhoneNumberId(Integer phoneNumberId) {
    this.phoneNumberId = phoneNumberId;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }
}
