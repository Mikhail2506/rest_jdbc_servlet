package by.toukach.restservlet.mapper;

import by.toukach.restservlet.dto.PhoneNumberDTO;
import by.toukach.restservlet.entity.PhoneNumber;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PhoneNumberMapper {

  PhoneNumber map(PhoneNumberDTO phoneNumberDTO);

  PhoneNumberDTO map(PhoneNumber phoneNumber);

  List<PhoneNumberDTO> map(List<PhoneNumber> phoneNumberList);

  List<PhoneNumber> mapUpdateList(List<PhoneNumberDTO> phoneNumberDTOList);

}
