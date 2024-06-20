package by.toukach.restservlet.servlet;

import by.toukach.restservlet.dto.PersonDTO;
import by.toukach.restservlet.entity.Person;
import by.toukach.restservlet.mapper.PersonMapper;
import by.toukach.restservlet.mapper.impl.PersonMapperImpl;
import by.toukach.restservlet.repository.PersonRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonRepositoryImpl;
import by.toukach.restservlet.service.PersonService;
import by.toukach.restservlet.service.serviceImpl.PersonServiceImpl;
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

//@WebServlet("/persons")
@WebServlet(urlPatterns = {"/persons/*"})
public class PersonsServlet extends HttpServlet {

    private final PersonMapper personMapper = PersonMapperImpl.getInstance();
    private final transient PersonService personService = PersonServiceImpl.getInstance();
    private final PersonRepository personRepository = PersonRepositoryImpl.getInstance();
    private final ObjectMapper objectMapper;

    public PersonsServlet() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void init() {

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
        String responseAnswer = "";

        PrintWriter printWriter = resp.getWriter();

        try {
            String[] pathPart = req.getPathInfo().split("/");
            if ("all".equals(pathPart[1])) {
                //printWriter.write("работает");
                List<Person> personList = personRepository.findAll();

                resp.setStatus(HttpServletResponse.SC_OK);
                responseAnswer = objectMapper.writeValueAsString(personList);
            } else {
                Long id = Long.parseLong(req.getParameter("id"));

                Person person = personMapper.map(personService.readPerson(id));
                resp.setStatus(HttpServletResponse.SC_OK);
                responseAnswer = String.valueOf(personMapper.map(person));
                // printWriter.write(responseAnswer);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Bad request.";
        }
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String json = getJson(req);

        String responseAnswer = "";
        Optional<PersonDTO> dto;
        try {
            dto = Optional.ofNullable(objectMapper.readValue(json, PersonDTO.class));
            PersonDTO userToSave = dto.orElseThrow(IllegalArgumentException::new);
            responseAnswer = objectMapper.writeValueAsString(personService.addPerson(userToSave));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect user Object.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
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
//        String[] pathPart = req.getPathInfo().split("/");
//        Long userId = Long.parseLong(pathPart[1]);

            Long personId = Long.parseLong(req.getParameter("id"));

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


//    public void controllerGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
//
//        String responseAnswer = "";
//        String action = req.getRequestURI();
//        //System.out.println(action);
//        String query = req.getQueryString();
//        //System.out.println(query);
//        // String action = req.getServletPath();
//        // System.out.println(action);
//
//        // resp.setContentType("application/json");
//
//        PrintWriter printWriter = resp.getWriter();
//
//        try {
//            if (action.equals("/id")) {
//                Long id = Long.parseLong(req.getParameter("id"));
////                PersonDTO dto = personMapper.entityToDto(personService.readPerson(id));
////                resp.setStatus(HttpServletResponse.SC_OK);
////               String responseAnswer = personMapper.toString(dto);
////                printWriter.write(personMapper.(dto));
//
//                PersonDTO dto = personMapper.entityToDto(personService.readPerson(id));
//                resp.setStatus(HttpServletResponse.SC_OK);
//                responseAnswer = personMapper.mapperDTOToJson(dto);
//                // printWriter.write(responseAnswer);
//            } else if (action.equals("/")) {
//                List<Person> personList = personRepository.findAll();
//
////                req.setAttribute("persons", personList);
////                req.getRequestDispatcher("/WEB-INF/persons.jsp").forward(req, resp);
////                personService.readPersons();
//
//                resp.setStatus(HttpServletResponse.SC_OK);
//                responseAnswer = objectMapper.writeValueAsString(personList);
//            }
//        } catch (Exception e) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            responseAnswer = "Bad request.";
//        }
//        printWriter = resp.getWriter();
//        printWriter.write(responseAnswer);
//        printWriter.flush();
//    }

//    public void controllerPost(HttpServletRequest req, HttpServletResponse resp) {
//        String uri = req.getRequestURI();
//        switch (uri) {
//            case "": {
//                String json = req.getQueryString();
//                PersonDTO dto = personMapper.mapperJsonToDTO(json);
//                personService.addPerson(dto);
//
//               // personService.addPerson(personMapper.dtoToEntity(dto));
//                break;
//            }
//        }
//    }

    //    public void controllerPut(HttpServletRequest req, HttpServletResponse resp) {
//        String uri = req.getRequestURI();
//        switch (uri) {
//            case "": {
////                String json = req.getQueryString();
////                PersonDTO dto = personMapper.mapperJsonToDTO(json);
////                personService.updatePerson(personMapper.dtoToEntity(dto));
////                break;
//
//
//                String json = req.getQueryString();
//                PersonToUpdateDTO dto = personToUpdateDTOMapper.mapperJsonToDTO(json);
//                personService.updatePerson(dto);
//
//
//                //personService.updatePerson(personMapper.dtoToEntity(dto));
//                break;
//            }
//        }
//    }
//
//    public void controllerDelete(HttpServletRequest req, HttpServletResponse resp) {
//        String uri = req.getRequestURI();
//        switch (uri) {
//            case "/id": {
//                personService.deletePerson(1L);
//                break;
//            }
//        }
//    }
//
    @Override
    public void destroy() {
    }
}
