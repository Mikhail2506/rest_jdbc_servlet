package by.toukach.restservlet.servlet;


import by.toukach.restservlet.dto.PersonSectionDTO;
import by.toukach.restservlet.exception.NotFoundException;
import by.toukach.restservlet.service.PersonSectionService;
import by.toukach.restservlet.service.serviceImpl.PersonSectionServiceImpl;
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

@WebServlet(urlPatterns = {"/department/*"})
public class PersonSectionServlet extends HttpServlet {
    private final transient PersonSectionService personSectionService = PersonSectionServiceImpl.getInstance();
    private final ObjectMapper objectMapper;

    public PersonSectionServlet() {
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

        String responseAnswer = "";
        try {
            String[] pathPart = req.getPathInfo().split("/");
            if ("all".equals(pathPart[1])) {
                List<PersonSectionDTO> personSectionDTOList = personSectionService.findAll();
                resp.setStatus(HttpServletResponse.SC_OK);
                responseAnswer = objectMapper.writeValueAsString(personSectionDTOList);
            } else {
                int departmentId = Integer.parseInt(pathPart[1]);
                PersonSectionDTO personSectionDTOList = personSectionService.findById(departmentId);
                resp.setStatus(HttpServletResponse.SC_OK);
                responseAnswer = objectMapper.writeValueAsString(personSectionDTOList);
            }
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseAnswer = e.getMessage();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Bad request.";
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
            String[] pathPart = req.getPathInfo().split("/");
            int personSectionId = Integer.parseInt(pathPart[1]);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            if (req.getPathInfo().contains("/deletePerson/")) {
                if ("deletePerson".equals(pathPart[2])) {
                    int personId = Integer.parseInt(pathPart[3]);
                    personSectionService.deletePersonFromSection(personSectionId, personId);
                }
            } else {
                personSectionService.delete(personSectionId);
            }
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseAnswer = e.getMessage();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Bad request. ";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String json = getJson(req);

        String responseAnswer = null;
        Optional<PersonSectionDTO> personSectionDTOList;
        try {
            personSectionDTOList = Optional.ofNullable(objectMapper.readValue(json, PersonSectionDTO.class));
            PersonSectionDTO personSectionToUpdateDTO1 = personSectionDTOList.orElseThrow(IllegalArgumentException::new);
            responseAnswer = objectMapper.writeValueAsString(personSectionService.save(personSectionToUpdateDTO1));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect department Object.";
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
        Optional<PersonSectionDTO> personSectionDTOList;
        try {
            if (req.getPathInfo().contains("/addPerson/")) {
                String[] pathPart = req.getPathInfo().split("/");
                if (pathPart.length > 3 && "addPerson".equals(pathPart[2])) {
                    int personSectionId = Integer.parseInt(pathPart[1]);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    int personId = Integer.parseInt(pathPart[3]);
                    personSectionService.addPersonToPersonSection(personSectionId, personId);
                }
            } else {
                personSectionDTOList = Optional.ofNullable(objectMapper.readValue(json, PersonSectionDTO.class));
                PersonSectionDTO sectionDtoToUpdate = personSectionDTOList.orElseThrow(IllegalArgumentException::new);
                personSectionService.update(sectionDtoToUpdate);
            }
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseAnswer = e.getMessage();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect department Object.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }
}
