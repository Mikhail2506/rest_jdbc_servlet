package by.toukach.restservlet.repositoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.toukach.restservlet.db.PropertiesUtil;
import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.dto.PersonSectionDTO;
import by.toukach.restservlet.dto.PhoneNumberDTO;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.repository.Impl.PersonRepositoryImpl;
import by.toukach.restservlet.repository.PersonRepository;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class PersonRepositoryTest {

  private static final String INIT_SQL = "sql/schema.sql";
  private static final int containerPort = 5432;
  private static final int localPort = 5432;

  @Container
  public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
      .withDatabaseName("postgres")
      .withUsername(PropertiesUtil.getProperties("db.username"))
      .withPassword(PropertiesUtil.getProperties("db.password"))
      .withExposedPorts(containerPort)
      .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
          new HostConfig().withPortBindings(
              new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
      ))
      .withInitScript(INIT_SQL);
  public static PersonRepository personRepository;
  private static JdbcDatabaseDelegate jdbcDatabaseDelegate;


  @BeforeAll
  static void beforeAll() {
    container.start();
    personRepository = PersonRepositoryImpl.getInstance();
    jdbcDatabaseDelegate = new JdbcDatabaseDelegate(container, "");
  }

  @AfterAll
  static void afterAll() {
    container.stop();
  }

  @BeforeEach
  void setUp() {
    ScriptUtils.runInitScript(jdbcDatabaseDelegate, INIT_SQL);
  }

  @Test
  void save() {
    String name = "Ivan";
    String surname = "Petrov";
    int age = 42;
    String phoneNumber = "+375 29 333 33 44";
    String section = "Бокс";
    PersonDTO dto = new PersonDTO();
    PhoneNumberDTO number = new PhoneNumberDTO(phoneNumber);
    List<PhoneNumberDTO> numberDTO = new ArrayList<>();
    numberDTO.add(number);

    PersonSectionDTO sectionDTO = new PersonSectionDTO(section);
    List<PersonSectionDTO> sectionDTOList = new ArrayList<>();
    sectionDTOList.add(sectionDTO);

    dto.setPersonName(name);
    dto.setPersonSurname(surname);
    dto.setPersonAge(age);
    dto.setPhoneNumberDTOList(numberDTO);
    dto.setPersonSectionDTOList(sectionDTOList);

    Person person = personRepository.save(dto);
    Optional<Person> resultUser = personRepository.findById(person.getPersonId());

    assertTrue(resultUser.isPresent());
    assertEquals(name, resultUser.get().getPersonName());
    assertEquals(surname, resultUser.get().getPersonSurname());
    assertEquals(age, resultUser.get().getPersonAge());
  }

  @Test
  void testFindById() {
    String name = "Ivan";
    String surname = "Petrov";
    int age = 42;
    String phoneNumber = "+375 29 333 33 44";
    String section = "Бокс";
    PersonDTO dto = new PersonDTO();
    PhoneNumberDTO number = new PhoneNumberDTO(phoneNumber);
    List<PhoneNumberDTO> numberDTO = new ArrayList<>();
    numberDTO.add(number);

    PersonSectionDTO sectionDTO = new PersonSectionDTO(section);
    List<PersonSectionDTO> sectionDTOList = new ArrayList<>();
    sectionDTOList.add(sectionDTO);

    dto.setPersonName(name);
    dto.setPersonSurname(surname);
    dto.setPersonAge(age);
    dto.setPhoneNumberDTOList(numberDTO);
    dto.setPersonSectionDTOList(sectionDTOList);

    Person savedPerson = personRepository.save(dto);

    Optional<Person> foundPerson = personRepository.findById(savedPerson.getPersonId());

    assertTrue(foundPerson.isPresent());
    assertEquals("Ivan", foundPerson.get().getPersonName());
    assertEquals("Petrov", foundPerson.get().getPersonSurname());
    assertEquals(42, foundPerson.get().getPersonAge());
  }

  @Test
  void testFindAll() {
    String nameDTO1 = "Ivan";
    String surnameDTO1 = "Petrov";
    int ageDTO1 = 42;
    String phoneNumberDTO1 = "+375 29 333 33 44";
    String sectionDTO1 = "Бокс";

    PersonDTO dto1 = new PersonDTO();
    PhoneNumberDTO numberOne = new PhoneNumberDTO(phoneNumberDTO1);
    List<PhoneNumberDTO> numberDTO1 = new ArrayList<>();
    numberDTO1.add(numberOne);

    PersonSectionDTO personSectionDTO1 = new PersonSectionDTO(sectionDTO1);
    List<PersonSectionDTO> personOneSectionDTOList = new ArrayList<>();
    personOneSectionDTOList.add(personSectionDTO1);

    dto1.setPersonName(nameDTO1);
    dto1.setPersonSurname(surnameDTO1);
    dto1.setPersonAge(ageDTO1);
    dto1.setPhoneNumberDTOList(numberDTO1);
    dto1.setPersonSectionDTOList(personOneSectionDTOList);

    String nameDTO2 = "Ivan";
    String surnameDTO2 = "Petrov";
    int ageDTO2 = 42;
    String phoneNumberDTO2 = "+375 29 333 33 44";
    String sectionDTO2 = "Хоккей";

    PersonDTO dto2 = new PersonDTO();
    PhoneNumberDTO numberTwo = new PhoneNumberDTO(phoneNumberDTO2);
    List<PhoneNumberDTO> numberDTO2 = new ArrayList<>();
    numberDTO1.add(numberTwo);

    PersonSectionDTO personSectionDTO2 = new PersonSectionDTO(sectionDTO2);
    List<PersonSectionDTO> personTwoSectionDTOList = new ArrayList<>();
    personOneSectionDTOList.add(personSectionDTO2);

    dto2.setPersonName(nameDTO2);
    dto2.setPersonSurname(surnameDTO2);
    dto2.setPersonAge(ageDTO2);
    dto2.setPhoneNumberDTOList(numberDTO2);
    dto2.setPersonSectionDTOList(personTwoSectionDTOList);

    personRepository.save(dto1);
    personRepository.save(dto2);

    List<Person> persons = personRepository.findAll();

    assertFalse(persons.isEmpty());
    assertEquals(6, persons.size());
  }

  @Test
  void testExistById() {
    String name = "Ivan";
    String surname = "Petrov";
    int age = 42;
    String phoneNumber = "+375 29 333 33 44";
    String section = "Бокс";
    PersonDTO dto = new PersonDTO();
    PhoneNumberDTO number = new PhoneNumberDTO(phoneNumber);
    List<PhoneNumberDTO> numberDTO = new ArrayList<>();
    numberDTO.add(number);

    PersonSectionDTO sectionDTO = new PersonSectionDTO(section);
    List<PersonSectionDTO> sectionDTOList = new ArrayList<>();
    sectionDTOList.add(sectionDTO);

    dto.setPersonName(name);
    dto.setPersonSurname(surname);
    dto.setPersonAge(age);
    dto.setPhoneNumberDTOList(numberDTO);
    dto.setPersonSectionDTOList(sectionDTOList);

    Person savedPerson = personRepository.save(dto);

    boolean exists = personRepository.existById(savedPerson.getPersonId());

    assertTrue(exists);
  }

  @Test
  void testDeletePersonById() {

    int expectedSize = personRepository.findAll().size();

    String name = "Nikolai";
    String surname = "Ivanov";
    int age = 56;
    String phoneNumber = "+375 29 666 77 88";
    String section = "Бокс";
    PersonDTO dto = new PersonDTO();
    PhoneNumberDTO number = new PhoneNumberDTO(phoneNumber);
    List<PhoneNumberDTO> numberDTO = new ArrayList<>();
    numberDTO.add(number);

    PersonSectionDTO sectionDTO = new PersonSectionDTO(section);
    List<PersonSectionDTO> sectionDTOList = new ArrayList<>();
    sectionDTOList.add(sectionDTO);

    dto.setPersonName(name);
    dto.setPersonSurname(surname);
    dto.setPersonAge(age);
    dto.setPhoneNumberDTOList(numberDTO);
    dto.setPersonSectionDTOList(sectionDTOList);

    Person savedPerson = personRepository.save(dto);

    personRepository.deletePersonById(savedPerson.getPersonId());
    int personListAfterDeletingSize = personRepository.findAll().size();

    Assertions.assertEquals(expectedSize, personListAfterDeletingSize);

  }

  @Test
  void testUpdate() {

    String name = "Nikolai";
    String surname = "Ivanov";
    int age = 56;
    String phoneNumber = "+375 29 666 77 88";
    String section = "Бокс";
    PersonDTO dto = new PersonDTO();
    PhoneNumberDTO number = new PhoneNumberDTO(phoneNumber);
    List<PhoneNumberDTO> numberDTO = new ArrayList<>();
    numberDTO.add(number);

    PersonSectionDTO sectionDTO = new PersonSectionDTO(section);
    List<PersonSectionDTO> sectionDTOList = new ArrayList<>();
    sectionDTOList.add(sectionDTO);

    dto.setPersonName(name);
    dto.setPersonSurname(surname);
    dto.setPersonAge(age);
    dto.setPhoneNumberDTOList(numberDTO);
    dto.setPersonSectionDTOList(sectionDTOList);

    Person savedPerson = personRepository.save(dto);

    savedPerson.setPersonName("Olga");
    savedPerson.setPersonSurname("Petrova");
    savedPerson.setPersonAge(35);

    personRepository.update(savedPerson);

    Optional<Person> updatedPerson = personRepository.findById(savedPerson.getPersonId());

    assertTrue(updatedPerson.isPresent());
    assertEquals("Olga", updatedPerson.get().getPersonName());
    assertEquals("Petrova", updatedPerson.get().getPersonSurname());
    assertEquals(35, updatedPerson.get().getPersonAge());
  }

}