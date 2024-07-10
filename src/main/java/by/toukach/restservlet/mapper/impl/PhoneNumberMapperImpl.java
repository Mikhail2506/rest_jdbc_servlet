package by.toukach.restservlet.mapper.impl;

import by.toukach.restservlet.dto.PersonSectionDTO;
import by.toukach.restservlet.dto.PhoneNumberDTO;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PhoneNumber;
import by.toukach.restservlet.mapper.PersonMapper;
import by.toukach.restservlet.mapper.PersonSectionMapper;
import by.toukach.restservlet.mapper.PhoneNumberMapper;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberMapperImpl implements PhoneNumberMapper {
    private static final PhoneNumberMapper phoneNumberMapper = PhoneNumberMapperImpl.getInstance();
    private static final PersonSectionMapper personSectionMapper = PersonSectionMapperImpl.getInstance();
    private static final PersonMapper personMapper = PersonMapperImpl.getInstance();
    private static PhoneNumberMapper instance;

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
                phoneNumberDTO.getNumber()
        );
    }

    @Override
    public PhoneNumberDTO map(PhoneNumber phoneNumber) {
        return new PhoneNumberDTO(
                phoneNumber.getNumber()
        );
    }
    @Override
    public List<PhoneNumberDTO> map(List<PhoneNumber> phoneNumberList) {
        List<PhoneNumberDTO> phoneNumberDTOList = new ArrayList<>();
        for (PhoneNumber phoneNumberAdd : phoneNumberList) {
            PhoneNumberDTO phoneNumberDTO = map(phoneNumberAdd);
            phoneNumberDTOList.add(phoneNumberDTO);
        }
        return phoneNumberDTOList;
    }

    @Override
    public List<PhoneNumber> mapUpdateList(List<PhoneNumberDTO> phoneNumberDTOList) {

        List<PhoneNumber> phoneNumberList = new ArrayList<>();
        for (PhoneNumberDTO phoneNumberDTOAdd : phoneNumberDTOList) {
            PhoneNumber phoneNumber = map(phoneNumberDTOAdd);
            phoneNumberList.add(phoneNumber);
        }
        return phoneNumberList;
    }
}



