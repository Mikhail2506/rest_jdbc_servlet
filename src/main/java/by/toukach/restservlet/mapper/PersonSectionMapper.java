package by.toukach.restservlet.mapper;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.dto.PersonSectionDTO;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.entity.PersonSection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class PersonSectionMapper implements MapperPersonSection {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MapperPersonSection mapperSection = MapperPersonSection.INSTANCE;

    @Override
    public PersonSection dtoToEntity(PersonSectionDTO dto) {
        PersonSection personSection = MapperPersonSection.INSTANCE.dtoToEntity(dto);
        return personSection;
    }

    @Override
    public PersonSectionDTO entityToDto(PersonSection entity) {
        PersonSectionDTO dto = MapperPersonSection.INSTANCE.entityToDto(entity);
        return dto;
    }

    @Override
    public List<PersonSection> dtoToEntityList(List<PersonSectionDTO> personSectionDTOList) {
        List<PersonSection> personSectionList = MapperPersonSection.INSTANCE.dtoToEntityList(personSectionDTOList);
        return personSectionList;
    }

    @Override
    public List<PersonSectionDTO> entityToDtoList(List<PersonSection> personSectionList) {
        List<PersonSectionDTO> personSectionDTOList = MapperPersonSection.INSTANCE.entityToDtoList(personSectionList);
        return personSectionDTOList;
    }
}
