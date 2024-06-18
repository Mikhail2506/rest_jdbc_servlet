package by.toukach.restservlet.mapper;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.dto.PersonToUpdateDTO;
import by.toukach.restservlet.entity.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {

    Person map(PersonDTO personDTO);

    Person map(PersonToUpdateDTO personToUpdateDTO);

    PersonToUpdateDTO map(Person person);

    List<PersonToUpdateDTO> map(List<Person> personList);

}
