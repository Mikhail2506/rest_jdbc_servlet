package by.toukach.restservlet.mapper;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.entity.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {

    Person map(PersonDTO personDTO);

    PersonDTO map(Person person);

    List<Person> mapUpdateList(List<PersonDTO> personDTODTOList);

    List<PersonDTO> map(List<Person> personList);

}
