package by.toukach.restservlet.mapper.impl;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.mapper.PersonMapper;
import by.toukach.restservlet.mapper.PersonSectionMapper;
import by.toukach.restservlet.mapper.PhoneNumberMapper;

import java.util.*;

public class PersonMapperImpl implements PersonMapper {
    private static final PhoneNumberMapper phoneNumberMapper = PhoneNumberMapperImpl.getInstance();
    private static final PersonSectionMapper personSectionMapper = PersonSectionMapperImpl.getInstance();

    private static PersonMapper instance;

    private PersonMapperImpl() {
    }

    public static synchronized PersonMapper getInstance() {
        if (instance == null) {
            instance = new PersonMapperImpl();
        }
        return instance;
    }

    @Override
    public by.toukach.restservlet.entity.Person map(PersonDTO personDTO) {
        return new by.toukach.restservlet.entity.Person(
                personDTO.getPersonId(),
                personDTO.getPersonName(),
                personDTO.getPersonSurname(),
                personDTO.getPersonAge(),
                phoneNumberMapper.mapUpdateList(personDTO.getPhoneNumberDTOList()),
                personSectionMapper.mapUpdateDTOList(personDTO.getPersonSectionDTOList())
        );
    }

    @Override
    public PersonDTO map(by.toukach.restservlet.entity.Person person) {
        return new PersonDTO(
                person.getPersonId(),
                person.getPersonName(),
                person.getPersonSurname(),
                person.getPersonAge(),
                phoneNumberMapper.map(person.getPhoneNumbersList()),
                personSectionMapper.mapUpdateList(person.getPersonSectionList())
        );
    }

    @Override
    public List<by.toukach.restservlet.entity.Person> mapUpdateList(List<PersonDTO> personDTODTOList) {

        List<by.toukach.restservlet.entity.Person> personList = new ArrayList<>();
        for (PersonDTO personDTODTOAdd : personDTODTOList) {
            by.toukach.restservlet.entity.Person person = map(personDTODTOAdd);
            personList.add(person);
        }
        return personList;
    }

    @Override
    public List<PersonDTO> map(List<by.toukach.restservlet.entity.Person> personList) {

        List<PersonDTO> personDTODTOList = new ArrayList<>();
        for (by.toukach.restservlet.entity.Person personTOAdd : personList) {
            PersonDTO personDTO = map(personTOAdd);
            personDTODTOList.add(personDTO);
        }
        return personDTODTOList;
    }

}
