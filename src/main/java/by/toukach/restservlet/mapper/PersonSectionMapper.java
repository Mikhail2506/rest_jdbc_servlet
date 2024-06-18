package by.toukach.restservlet.mapper;

import by.toukach.restservlet.dto.PersonSectionDTO;
import by.toukach.restservlet.dto.PersonSectionToUpdateDTO;
import by.toukach.restservlet.entity.PersonSection;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PersonSectionMapper {

    PersonSection map(PersonSectionToUpdateDTO personSectionToUpdateDTO);

    PersonSectionDTO map(PersonSection personSection);

    PersonSection map(PersonSectionToUpdateDTO personSectionToUpdateDTO);

    List<PersonSectionDTO> map(List<PersonSection> personSectionList);

    List<PersonSection> mapUpdateList(List<PersonSectionToUpdateDTO> personSectionToUpdateDTOList);

}
