package by.toukach.restservlet.mapper;

import by.toukach.restservlet.dto.PersonToUpdateDTO;
import by.toukach.restservlet.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MapperPersonToUpdate {
    MapperPersonToUpdate INSTANCE = Mappers.getMapper(MapperPersonToUpdate.class);

    Person dtoToUpdateToEntity(PersonToUpdateDTO dto);

    PersonToUpdateDTO entityToUpdateDto(Person person);

    List<PersonToUpdateDTO> entityToUpdateDtoToList(List<Person> person);

    List<Person> dtoToUpdateToEntityList(List<PersonToUpdateDTO> personToUpdateDTOList);
}
