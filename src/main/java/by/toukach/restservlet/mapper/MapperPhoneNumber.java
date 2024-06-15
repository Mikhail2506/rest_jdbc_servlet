package by.toukach.restservlet.mapper;

import by.toukach.restservlet.dto.PhoneNumberDTO;
import by.toukach.restservlet.entity.PhoneNumber;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MapperPhoneNumber {

    MapperPhoneNumber INSTANCE = Mappers.getMapper(MapperPhoneNumber.class);

    PhoneNumber dtoToEntity(PhoneNumberDTO dto);

    PhoneNumberDTO entityToDto(PhoneNumber entity);

    List<PhoneNumberDTO> entityToDtoList(List<PhoneNumber> phoneNumberList);

    List<PhoneNumber> dtoToEntityList(List<PhoneNumberDTO> phoneNumberDTOList);
}
