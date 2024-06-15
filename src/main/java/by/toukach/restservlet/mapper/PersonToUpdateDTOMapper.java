package by.toukach.restservlet.mapper;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.dto.PersonToUpdateDTO;
import by.toukach.restservlet.entity.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class PersonToUpdateDTOMapper implements MapperPersonToUpdate, Mapper<PersonToUpdateDTO, Person > {

    private final MapperPersonToUpdate mapperPersonToUpdate = MapperPersonToUpdate.INSTANCE;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Person dtoToUpdateToEntity(PersonToUpdateDTO dto) {
        Person person = MapperPersonToUpdate.INSTANCE.dtoToUpdateToEntity(dto);
        return person;
    }

    @Override
    public PersonToUpdateDTO entityToUpdateDto(Person person) {
        if (person == null) {
            return null;
        }
        PersonToUpdateDTO dto = mapperPersonToUpdate.INSTANCE.entityToUpdateDto(person);
        return dto;
    }

    @Override
    public List<PersonToUpdateDTO> entityToUpdateDtoToList(List<Person> personList) {
        if (personList == null) {
            return null;
        }
        List<PersonToUpdateDTO> entityToUpdateDtoToList = mapperPersonToUpdate.entityToUpdateDtoToList(personList);
        return entityToUpdateDtoToList;
    }

    @Override
    public List<Person> dtoToUpdateToEntityList(List<PersonToUpdateDTO> personToUpdateDTOList) {
        if (personToUpdateDTOList == null) {
            return null;
        }
        List<Person> personList = mapperPersonToUpdate.dtoToUpdateToEntityList(personToUpdateDTOList);
        return personList;
    }

    @Override
    public PersonToUpdateDTO mapperJsonToDTO(String json) {
        try {
            return objectMapper.readValue(json, PersonToUpdateDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String mapperDTOToJson(PersonToUpdateDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
