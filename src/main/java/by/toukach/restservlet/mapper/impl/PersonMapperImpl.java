package by.toukach.restservlet.mapper.impl;


import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.dto.PersonToUpdateDTO;
import by.toukach.restservlet.entity.Person;
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
    public Person map(PersonDTO personDTO) {
        return new Person(
                (Long) null,
                personDTO.getPersonName(),
                personDTO.getPersonSurname(),
                (long) personDTO.getPersonAge(),
                phoneNumberMapper.map(personDTO.getPhoneNumberDTOList()),
                personSectionMapper.mapUpdateList(personDTO.getPersonSectionDTOList())
        );
    }

    @Override
    public Person map(PersonToUpdateDTO personToUpdateDTO) {
        return new Person(
                personToUpdateDTO.getPersonId(),
                personToUpdateDTO.getPersonName(),
                personToUpdateDTO.getPersonSurname(),
                personToUpdateDTO.getPersonAge(),
                phoneNumberMapper.map(personToUpdateDTO.getPhoneNumberToUpdateDTOs()),//List<PhoneNumberDTO><-List<PhoneNumberToUpdateDTO>
                personSectionMapper.mapUpdateList(personToUpdateDTO.getPersonSectionToUpdateDTOs())
        );
    }

    @Override
    public PersonToUpdateDTO map(Person person) {
        return new PersonToUpdateDTO(
                person.getPersonId(),
                person.getPersonName(),
                person.getPersonSurname(),
                person.getPersonAge(),
                phoneNumberMapper.map(person.getPhoneNumbersList()),
                personSectionMapper.map(person.getPersonSectionsList())
        );
    }

    @Override
    public List<PersonToUpdateDTO> map(List<Person> personList) {
        List<PersonToUpdateDTO> personToUpdateDTOList = new ArrayList<>();
        for (Person person : personList) {
            PersonToUpdateDTO personToUpdateDTO = map(person);
            personToUpdateDTOList.add(personToUpdateDTO);
        }
        return personToUpdateDTOList;
    }
}
