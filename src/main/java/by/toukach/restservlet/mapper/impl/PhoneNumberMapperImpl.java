package by.toukach.restservlet.mapper.impl;

import by.toukach.restservlet.dto.PhoneNumberDTO;
import by.toukach.restservlet.dto.PhoneNumberToUpdateDTO;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PhoneNumber;
import by.toukach.restservlet.mapper.PhoneNumberMapper;

import java.util.ArrayList;
import java.util.List;


public class PhoneNumberMapperImpl implements PhoneNumberMapper {

    private static PhoneNumberMapper instance;
    private PhoneNumberToUpdateDTO phoneNumberToUpdateDTO;

    private PhoneNumberMapperImpl() {
    }

    public static synchronized PhoneNumberMapper getInstance() {
        if (instance == null) {
            instance = new PhoneNumberMapperImpl();
        }
        return instance;
    }

    @Override
    public PhoneNumber map(PhoneNumberDTO phoneNumberDTO) {
               return new PhoneNumber(
               null,
                       phoneNumberDTO.getNumber(),
                       phoneNumberDTO.getPerson()

        );
    }

    @Override
    public PhoneNumberDTO map(PhoneNumber phoneNumber) {
        return new PhoneNumberDTO(
                phoneNumber.getId(),
                phoneNumber.getNumber(),
                phoneNumber.getUser() == null ?
                        null :
                        new UserSmallOutGoingDto(
                                phoneNumber.getUser().getId(),
                                phoneNumber.getUser().getFirstName(),
                                phoneNumber.getUser().getLastName()
                        )
        );
    }

    @Override
    public List<PhoneNumberDTO> map(List<PhoneNumber> phoneNumberList) {
        return List.of();
    }

    @Override
    public List<PhoneNumber> mapUpdateList(List<PhoneNumberToUpdateDTO> phoneNumberToUpdateDTOList) {
        return List.of();
    }

    @Override
    public PhoneNumber map(PhoneNumberToUpdateDTO phoneNumberToUpdateDTO) {
        return null;
    }


//    @Override
//    public PhoneNumber mapUpdateList(PhoneNumberDTO phoneNumberDTO) {
//        return new PhoneNumber(
//                null,
//                phoneNumberDTO.getNumber(),
//                phoneNumberDTO.getPerson()
//        );
//    }
//
//    @Override
//    public PhoneNumberToUpdateDTO mapUpdateList(PhoneNumber phoneNumber) {
//        return new PhoneNumberToUpdateDTO(
//                phoneNumber.getPhoneNumberId(),
//                phoneNumber.getNumber(),
//                phoneNumber.getPerson() == null ?
//                        null :
//                        phoneNumber.getPerson().getPersonId());
//    }
//
//    @Override
//    public List<PhoneNumber> mapUpdateList(List<PhoneNumberToUpdateDTO> phoneNumberToUpdateDTOList) {
//
//        List<PhoneNumber> phoneNumberList = new ArrayList<>();
//        for (PhoneNumberToUpdateDTO phoneNumberApdating : phoneNumberToUpdateDTOList) {
//            PhoneNumber phoneNumber = map(phoneNumberApdating);
//            phoneNumberList.add(phoneNumber);
//        }
//        return phoneNumberList;
//    }
//
//    @Override
//    public List<PhoneNumberToUpdateDTO> mapUpdateList(List<PhoneNumber> phoneNumberList) {
//
//        List<PhoneNumberToUpdateDTO> phoneNumberToUpdateDTOList = new ArrayList<>();
//        for (PhoneNumber phoneNumber : phoneNumberList) {
//            PhoneNumberToUpdateDTO phoneNumberToUpdateDTOAdd = mapUpdateList(phoneNumber);
//            phoneNumberToUpdateDTOList.add(phoneNumberToUpdateDTOAdd);
//        }
//        return phoneNumberToUpdateDTOList;
//    }
//
//    @Override
//    public List<PhoneNumber> mapUpdateList(List<PhoneNumberDTO> phoneNumberDTOList) {
//
//        List<PhoneNumber> phoneNumberList = new ArrayList<>();
//        for (PhoneNumberDTO phoneNumberDTOAdd : phoneNumberDTOList) {
//            PhoneNumber phoneNumberAdd = mapUpdateList(phoneNumberDTOAdd);
//            phoneNumberList.add(phoneNumberAdd);
//        }
//        return phoneNumberList;
//    }
//
//    @Override
//    public List<PhoneNumberDTO> mapUpdateList(List<PhoneNumber> phoneNumberList) {
//
//        List<PhoneNumberDTO> phoneNumberDTOList = new ArrayList<>();
//        for (PhoneNumber phoneNumberAdd : phoneNumberList) {
//            PhoneNumberDTO phoneNumberDTOAdd = mapUpdateList(phoneNumberAdd);
//            phoneNumberDTOList.add(phoneNumberDTOAdd);
//        }
//        return phoneNumberDTOList;
//    }
//
//    @Override
//    public PhoneNumber map(PhoneNumberToUpdateDTO phoneNumberToUpdateDTO) {
//        return new PhoneNumber(
//                phoneNumberToUpdateDTO.getNumberId(),
//                phoneNumberToUpdateDTO.getNumber(),
//                new Person(
//                        phoneNumberToUpdateDTO.getPersonId(),
//                        null,
//                        null,
//                        null,
//                        List.of(),
//                        List.of()
//                )
//        );
    }
}

