package by.toukach.restservlet.mapper.impl;

import by.toukach.restservlet.dto.PersonDTO;
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
        personDTO.getPersonId(),
        personDTO.getPersonName(),
        personDTO.getPersonSurname(),
        personDTO.getPersonAge(),
        phoneNumberMapper.mapUpdateList(personDTO.getPhoneNumberDTOList()),
        personSectionMapper.mapUpdateDTOList(personDTO.getPersonSectionDTOList())
    );
  }

  @Override
  public PersonDTO map(Person person) {
    return new PersonDTO(
        person.getPersonId(),
        person.getPersonName(),
        person.getPersonSurname(),
        person.getPersonAge(),
        phoneNumberMapper.map(person.getPhoneNumbersList()),
        personSectionMapper.map(person.getPersonSectionList())
    );
  }

  @Override
  public List<Person> mapUpdateList(List<PersonDTO> personDTODTOList) {

    List<Person> personList = new ArrayList<>();
    for (PersonDTO personDTOAdd : personDTODTOList) {
      Person person = map(personDTOAdd);
      personList.add(person);
    }
    return personList;
  }

  @Override
  public List<PersonDTO> map(List<Person> personList) {

    List<PersonDTO> personDTOList = new ArrayList<>();
    for (Person personTOAdd : personList) {
      PersonDTO personDTO = map(personTOAdd);
      personDTOList.add(personDTO);
    }
    return personDTOList;
  }
}
