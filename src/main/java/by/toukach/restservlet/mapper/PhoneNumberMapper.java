package by.toukach.restservlet.mapper;

import by.toukach.restservlet.dto.PhoneNumberDTO;
import by.toukach.restservlet.entity.PhoneNumber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class PhoneNumberMapper implements MapperPhoneNumber {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PhoneNumber dtoToEntity(PhoneNumberDTO dto) {
        PhoneNumber phoneNumber = MapperPhoneNumber.INSTANCE.dtoToEntity(dto);
        return phoneNumber;
    }

    @Override
    public PhoneNumberDTO entityToDto(PhoneNumber entity) {
        PhoneNumberDTO dto = MapperPhoneNumber.INSTANCE.entityToDto(entity);
        return dto;
    }

    @Override
    public List<PhoneNumberDTO> entityToDtoList(List<PhoneNumber> phoneNumberList) {
        List<PhoneNumberDTO> phoneNumberDTOList = MapperPhoneNumber.INSTANCE.entityToDtoList(phoneNumberList);
        return phoneNumberDTOList;
    }

    @Override
    public List<PhoneNumber> dtoToEntityList(List<PhoneNumberDTO> phoneNumberDTOList) {
        List<PhoneNumber> phoneNumberList = MapperPhoneNumber.INSTANCE.dtoToEntityList(phoneNumberDTOList);
        return phoneNumberList;
    }

    public String mapperDTOToJson(PhoneNumberDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (IOException e) {
            throw new RuntimeException("Error converting Person to JSON", e);
        }
    }
}

