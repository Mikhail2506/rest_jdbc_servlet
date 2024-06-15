package by.toukach.restservlet.mapper;

import by.toukach.restservlet.dto.PersonSectionDTO;
import by.toukach.restservlet.entity.PersonSection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MapperPersonSection {

    MapperPersonSection INSTANCE = Mappers.getMapper(MapperPersonSection.class);

    PersonSection dtoToEntity(PersonSectionDTO dto);

    PersonSectionDTO entityToDto(PersonSection personsection);

    List<PersonSection> dtoToEntityList(List<PersonSectionDTO> personSectionDTOList);

    List<PersonSectionDTO> entityToDtoList(List<PersonSection> personSectionList);
}
