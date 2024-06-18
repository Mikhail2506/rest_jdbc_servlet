package by.toukach.restservlet.mapper.impl;

import by.toukach.restservlet.dto.PersonSectionDTO;
import by.toukach.restservlet.dto.PersonSectionToUpdateDTO;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.mapper.PersonSectionMapper;

import java.util.List;

public class PersonSectionMapperImpl implements PersonSectionMapper {

    private static PersonSectionMapper instance;

    private PersonSectionMapperImpl() {
    }

    public static synchronized PersonSectionMapper getInstance() {
        if (instance == null) {
            instance = new PersonSectionMapperImpl();
        }
        return instance;
    }

    @Override
    public PersonSectionDTO map(PersonSection personSection) {
        return null;
    }

    @Override
    public PersonSection map(PersonSectionToUpdateDTO personSectionToUpdateDTO) {
        return null;
    }

    @Override
    public List<PersonSectionDTO> map(List<PersonSection> personSectionList) {
        return List.of();
    }

    @Override
    public List<PersonSection> mapUpdateList(List<PersonSectionToUpdateDTO> personSectionToUpdateDTOList) {
        return List.of();
    }
}
