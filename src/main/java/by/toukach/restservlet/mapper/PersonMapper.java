package by.toukach.restservlet.mapper;


import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.entity.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class PersonMapper implements MapperPerson, Mapper<PersonDTO, Person> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Person dtoToEntity(PersonDTO dto) {
        if (dto == null) {
            return null;
        }
        Person person = MapperPerson.INSTANCE.dtoToEntity(dto);
        return person;
    }

    @Override
    public PersonDTO entityToDto(Person entity) {
        if (entity == null) {
            return null;
        }
        PersonDTO dto = MapperPerson.INSTANCE.entityToDto(entity);
        return dto;
    }

    @Override
    public List<Person> dtoToEntityList(List<PersonDTO> personDTOList) {
        List<Person> personList = MapperPerson.INSTANCE.dtoToEntityList(personDTOList);
        return personList;
    }

    @Override
    public List<PersonDTO> entityToDtoList(List<Person> personList) {
        if (personList == null) {
            return null;
        }
        List<PersonDTO> personDtoList = MapperPerson.INSTANCE.entityToDtoList(personList);
        return personDtoList;
    }

    @Override
    public PersonDTO mapperJsonToDTO(String json) {
        try {
            return objectMapper.readValue(json, PersonDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String mapperDTOToJson(PersonDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
