package by.toukach.restservlet.mapper;

import by.toukach.restservlet.dto.PersonDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {

    by.toukach.restservlet.entity.Person map(PersonDTO personDTO);

    PersonDTO map(by.toukach.restservlet.entity.Person person);

    List<by.toukach.restservlet.entity.Person> mapUpdateList(List<PersonDTO> personDTODTOList);

    List<PersonDTO> map(List<by.toukach.restservlet.entity.Person> personList);

}
