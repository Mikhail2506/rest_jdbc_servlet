package by.toukach.restservlet.servlet;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.exception.NotFoundException;
import by.toukach.restservlet.service.PersonService;
import by.toukach.restservlet.serviceImpl.PersonServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PersonServletTest {

  private static PersonService mockPersonService;

  @InjectMocks
  private static PersonServlet personServlet;

  private static PersonServiceImpl oldInstance;
  @Mock
  private HttpServletRequest mockRequest;
  @Mock
  private HttpServletResponse mockResponse;


  private ObjectMapper objectMapper;
  private StringWriter stringWriter;
  private PrintWriter writer;

  private static void setMock(PersonService mock) {
    try {
      Field instance = PersonServiceImpl.class.getDeclaredField("instance");
      instance.setAccessible(true);
      oldInstance = (PersonServiceImpl) instance.get(instance);
      instance.set(instance, mock);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @BeforeAll
  static void beforeAll() {
    mockPersonService = Mockito.mock(PersonService.class);
    setMock(mockPersonService);
    personServlet = new PersonServlet();
  }

  @AfterAll
  static void afterAll() throws Exception {
    Field instance = PersonServiceImpl.class.getDeclaredField("instance");
    instance.setAccessible(true);
    instance.set(instance, oldInstance);
  }

  @BeforeEach
  void setUp() throws IOException {

    objectMapper = new ObjectMapper();
    stringWriter = new StringWriter();
    writer = new PrintWriter(stringWriter);
    when(mockResponse.getWriter()).thenReturn(writer);

  }

  @AfterEach
  void tearDown() {
    Mockito.reset(mockPersonService);
  }

  @Test
  void testDoGetAllPersons() throws Exception {

    Mockito.doReturn("persons/all").when(mockRequest).getRequestURI();

    personServlet.doGet(mockRequest, mockResponse);

    verify(mockPersonService).readPersons();

  }

  @Test
  void testDoGetSinglePersonFound()
      throws IOException, NotFoundException, SQLException, ServletException {
    Mockito.doReturn("persons/2").when(mockRequest).getRequestURI();

    personServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockPersonService).readPerson(Mockito.anyInt());
  }

  @Test
  void doGetBadRequest() throws IOException, ServletException {
    Mockito.doReturn("persons/2q").when(mockRequest).getRequestURI();

    personServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
  }

  @Test
  void testDoGetSinglePersonNotFound()
      throws ServletException, IOException, SQLException, NotFoundException {

    when(mockRequest.getRequestURI()).thenReturn("persons/1");

    when(mockPersonService.readPerson(1)).thenReturn(null);

    personServlet.doGet(mockRequest, mockResponse);

    verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    Assertions.assertEquals("Person not found with id: 1", stringWriter.toString());
  }

  @Test
  void testDoGetInvalidIdFormat() throws Exception {

    when(mockRequest.getRequestURI()).thenReturn("/persons/invalid");

    personServlet.doGet(mockRequest, mockResponse);

    verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    assertEquals("Invalid ID format.", stringWriter.toString());
  }

  @Test
  void testDoGetInternalServerError() throws Exception {

    when(mockRequest.getRequestURI()).thenReturn("/persons/1");
    when(mockPersonService.readPerson(1)).thenThrow(new RuntimeException("Database error"));

    personServlet.doGet(mockRequest, mockResponse);

    verify(mockResponse).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    assertEquals("Internal server error.", stringWriter.toString());
  }
}
