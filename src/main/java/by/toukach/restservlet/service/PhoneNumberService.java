package by.toukach.restservlet.service;

import by.toukach.restservlet.dto.PhoneNumberDTO;
import by.toukach.restservlet.entity.PhoneNumber;
import by.toukach.restservlet.exception.NotFoundException;

import java.util.List;

public interface PhoneNumberService {

    PhoneNumber save(PhoneNumberDTO phoneNumberDTO);

    void update(PhoneNumberDTO phoneNumberDTO) throws NotFoundException;

    PhoneNumberDTO findById(Long phoneNumberId) throws NotFoundException;

    List<PhoneNumberDTO> findAll();

    boolean delete(Long phoneNumberId);
}
