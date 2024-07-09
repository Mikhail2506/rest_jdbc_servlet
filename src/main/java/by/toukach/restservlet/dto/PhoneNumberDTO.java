package by.toukach.restservlet.dto;

public class PhoneNumberDTO {

    // private Integer phoneNumberDTOId;

    private String number;

    public PhoneNumberDTO() {
    }

//    public Integer getPhoneNumberDTOId() {
//        return phoneNumberDTOId;
//    }
//
//    public void setPhoneNumberDTOId(Integer phoneNumberDTOId) {
//        this.phoneNumberDTOId = phoneNumberDTOId;
//    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

//    public PhoneNumberDTO(Integer phoneNumberDTOId, String number) {
//        this.phoneNumberDTOId = phoneNumberDTOId;
//        this.number = number;
//    }

    public PhoneNumberDTO(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneNumberDTO{" +
                "number='" + number + '\'' +
                '}';
    }
}
