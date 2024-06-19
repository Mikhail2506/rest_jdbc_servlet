package by.toukach.restservlet.service.serviceImpl;

import by.toukach.restservlet.dto.PhoneNumberDTO;
import by.toukach.restservlet.entity.PhoneNumber;
import by.toukach.restservlet.exception.NotFoundException;
import by.toukach.restservlet.service.PhoneNumberService;

import java.util.List;

public class PhoneNumberServiceImpl implements PhoneNumberService {

    @Override
    public PhoneNumber save(PhoneNumberDTO phoneNumberDTO) {
        return null;
    }

    @Override
    public void update(PhoneNumberDTO phoneNumberDTO) throws NotFoundException {

    }

    @Override
    public PhoneNumberDTO findById(Long phoneNumberId) throws NotFoundException {
        return null;
    }

    @Override
    public List<PhoneNumberDTO> findAll() {
        return List.of();
    }

    @Override
    public boolean delete(Long phoneNumberId) {
        return false;
    }
}
