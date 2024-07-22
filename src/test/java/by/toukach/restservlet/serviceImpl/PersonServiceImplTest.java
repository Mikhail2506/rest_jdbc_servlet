package by.toukach.restservlet.serviceImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.exception.NotFoundException;
import by.toukach.restservlet.repository.PersonRepository;
import by.toukach.restservlet.repository.Impl.PersonRepositoryImpl;
import by.toukach.restservlet.service.Impl.PersonServiceImpl;
import by.toukach.restservlet.service.PersonService;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class PersonServiceImplTest {

  private static PersonService personService;
  private static PersonRepository mockPersonRepository;
  private static PersonRepositoryImpl oldInstance;

  private static void setMock(PersonRepository mock) {
    try {
      Field instance = PersonRepositoryImpl.class.getDeclaredField("instance");
      instance.setAccessible(true);
      oldInstance = (PersonRepositoryImpl) instance.get(instance);
      instance.set(instance, mock);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @BeforeAll
  static void beforeAll() {
    mockPersonRepository = mock(PersonRepository.class);
    setMock(mockPersonRepository);
    personService = PersonServiceImpl.getInstance();
  }

  @AfterAll
  static void afterAll() throws Exception {
    Field instance = PersonRepositoryImpl.class.getDeclaredField("instance");
    instance.setAccessible(true);
    instance.set(instance, oldInstance);
  }

  @BeforeEach
  void setUp() {
    Mockito.reset(mockPersonRepository);
  }

  @Test
  void addPerson() {

    int expectedId = 1;

    PersonDTO dto = new PersonDTO("f1 name", "l1 name", 43, List.of(), List.of());
    Person saved = new Person(expectedId, "f1 name", "l1 name", 43, List.of(), List.of());

    Mockito.doReturn(saved).when(mockPersonRepository).save(Mockito.any(PersonDTO.class));

    PersonDTO result = personService.addPerson(dto);

    Assertions.assertEquals(expectedId, result.getPersonId());
  }

  @Test
  void updatePerson() throws NotFoundException {
    int expectedId = 11;

    PersonDTO dto = new PersonDTO(expectedId, "f1 name", "l1 name", 45,
        List.of(), List.of());

    Mockito.doReturn(true).when(mockPersonRepository).existById(Mockito.anyInt());

    personService.updatePerson(dto);

    ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
    verify(mockPersonRepository).update(argumentCaptor.capture());

    Person result = argumentCaptor.getValue();
    Assertions.assertEquals(expectedId, result.getPersonId());
  }

  @Test
  void readingPersonNotFound() {

    Optional<Person> person = Optional.empty();
    Mockito.doReturn(false).when(mockPersonRepository).existById(Mockito.anyInt());

    NotFoundException exception = Assertions.assertThrows(
        NotFoundException.class,
        () -> {
          personService.readPerson(1);
        }, "Not found."
    );
    Assertions.assertEquals("User not found.", exception.getMessage());
  }

  @Test
  void readPerson() throws NotFoundException, SQLException {
    int expectedId = 1;

    Optional<Person> person = Optional.of(
        new Person(expectedId, "f1 name", "l1 name", 43, List.of(), List.of()));

    Mockito.doReturn(true).when(mockPersonRepository).existById(Mockito.anyInt());
    Mockito.doReturn(person).when(mockPersonRepository).findById(Mockito.anyInt());

    PersonDTO dto = personService.readPerson(expectedId);

    Assertions.assertEquals(expectedId, dto.getPersonId());
  }

  @Test
  void readPersons() throws SQLException {
    personService.readPersons();
    Mockito.verify(mockPersonRepository).findAll();
  }

  @Test
  void deletePerson() throws NotFoundException {
    int expectedId = 100;

    Mockito.doReturn(true).when(mockPersonRepository).existById(Mockito.anyInt());
    personService.deletePerson(expectedId);

    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(mockPersonRepository).deletePersonById(argumentCaptor.capture());

    int result = argumentCaptor.getValue();
    Assertions.assertEquals(expectedId, result);
  }

}