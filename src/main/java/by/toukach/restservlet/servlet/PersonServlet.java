package by.toukach.restservlet.servlet;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.service.PersonService;
import by.toukach.restservlet.service.Impl.PersonServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/persons/*")
public class PersonServlet extends HttpServlet {

  private final  transient PersonService personService = PersonServiceImpl.getInstance();
  private final ObjectMapper objectMapper;

  public PersonServlet() {
    this.objectMapper = new ObjectMapper();
  }

  private static void setJsonHeader(HttpServletResponse resp) {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
  }

  private static String getJson(HttpServletRequest req) throws IOException {
    StringBuilder sb = new StringBuilder();
    BufferedReader postData = req.getReader();
    String line;
    while ((line = postData.readLine()) != null) {
      sb.append(line);
    }
    return sb.toString();
  }


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    setJsonHeader(resp);
    PrintWriter printWriter = resp.getWriter();
    String responseAnswer = "";

    try {
      String requestURI = req.getRequestURI();
      String[] parts = requestURI.split("/");
      String parameter = parts[parts.length - 1];

      if ("all".equals(parameter)) {
        List<PersonDTO> personList = personService.readPersons();
        resp.setStatus(HttpServletResponse.SC_OK);
        responseAnswer = objectMapper.writeValueAsString(personList);
      } else {
        int id = Integer.parseInt(parameter);
        PersonDTO personDTO = personService.readPerson(id);
        if (personDTO != null) {
          resp.setStatus(HttpServletResponse.SC_OK);
          responseAnswer = objectMapper.writeValueAsString(personDTO);
        } else {
          resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
          responseAnswer = "Person not found with id: " + id;
        }
      }
    } catch (NumberFormatException e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseAnswer = "Invalid ID format.";
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      responseAnswer = "Internal server error.";
    } finally {
      printWriter.write(responseAnswer);
      printWriter.flush();
    }
  }

  @Override
  protected void doPost(HttpServletRequest request,
      HttpServletResponse resp) throws IOException {

    setJsonHeader(resp);
    String json = getJson(request);

    try {
      PersonDTO personDTOSave = objectMapper.readValue(json, PersonDTO.class);
      PersonDTO savedPerson = personService.addPerson(personDTOSave);
      String responseAnswer = objectMapper.writeValueAsString(savedPerson);
      PrintWriter printWriter = resp.getWriter();
      printWriter.write(responseAnswer);
      printWriter.flush();
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write("Incorrect user Object.");
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    setJsonHeader(resp);
    String json = getJson(req);

    String responseAnswer = "";
    Optional<PersonDTO> personUpdating;
    try {
      personUpdating = Optional.ofNullable(objectMapper.readValue(json, PersonDTO.class));
      PersonDTO personDTO = personUpdating.orElseThrow(IllegalArgumentException::new);
      personService.updatePerson(personDTO);
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseAnswer = "Incorrect user Object.";
    }
    PrintWriter printWriter = resp.getWriter();
    printWriter.write(responseAnswer);
    printWriter.flush();
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    setJsonHeader(resp);
    String responseAnswer = "";

    try {
      String requestURI = req.getRequestURI();
      String[] parts = requestURI.split("/");
      String parameter = parts[parts.length - 1];
      int personId = Integer.parseInt(parameter);
      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
      personService.deletePerson(personId);
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseAnswer = "Bad request.";
    }
    PrintWriter printWriter = resp.getWriter();
    printWriter.write(responseAnswer);
    printWriter.flush();
  }

}
