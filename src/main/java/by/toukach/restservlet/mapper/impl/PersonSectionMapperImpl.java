package by.toukach.restservlet.mapper.impl;

import by.toukach.restservlet.dto.PersonSectionDTO;
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
    public PersonSection map(PersonSectionDTO personSectionDTO) {
        return new PersonSection(
                null,
                personSectionDTO.getSectionName(),
                null
        );
    }

    @Override
    public PersonSectionDTO map(PersonSection personSection) {
        return null;
    }

    @Override
    public List<PersonSectionDTO> mapUpdateList(List<PersonSection> personSectionList) {
        return List.of();
    }

    @Override
    public List<PersonSection> mapUpdateDTOList(List<PersonSectionDTO> personSectionDTOList) {
        return List.of();
    }
}
