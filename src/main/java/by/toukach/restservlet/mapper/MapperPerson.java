package by.toukach.restservlet.mapper;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.entity.Person;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapperPerson {

    MapperPerson INSTANCE = Mappers.getMapper(MapperPerson.class);

    Person dtoToEntity(PersonDTO dto);

    PersonDTO entityToDto(Person person);

    List<Person> dtoToEntityList(List<PersonDTO> person);

    List<PersonDTO> entityToDtoList(List<Person> personList);

}
