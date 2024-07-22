package by.toukach.restservlet.mapper;

import by.toukach.restservlet.dto.PersonSectionDTO;
import by.toukach.restservlet.entity.PersonSection;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PersonSectionMapper {

  PersonSection map(PersonSectionDTO personSectionDTO);

  PersonSectionDTO map(PersonSection personSection);

  List<PersonSection> mapUpdateDTOList(List<PersonSectionDTO> personSectionDTOList);

  List<PersonSectionDTO> map(List<PersonSection> personSectionList);
}
