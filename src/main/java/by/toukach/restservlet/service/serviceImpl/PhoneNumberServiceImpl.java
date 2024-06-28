package by.toukach.restservlet.service.serviceImpl;

import by.toukach.restservlet.dto.PhoneNumberDTO;
import by.toukach.restservlet.entity.PhoneNumber;
import by.toukach.restservlet.exception.NotFoundException;
import by.toukach.restservlet.mapper.PhoneNumberMapper;
import by.toukach.restservlet.mapper.impl.PhoneNumberMapperImpl;
import by.toukach.restservlet.repository.PhoneNumbersRepository;
import by.toukach.restservlet.repository.repositoryImpl.PhoneNumbersRepositoryImpl;
import by.toukach.restservlet.service.PhoneNumberService;

import java.util.List;

public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberMapper phoneNumberMapper = PhoneNumberMapperImpl.getInstance();
    private static PhoneNumberService instance;
    private final PhoneNumbersRepository phoneNumbersRepository = PhoneNumbersRepositoryImpl.getInstance();

    @Override
    public PhoneNumber save(PhoneNumberDTO phoneNumberDTO) {
        PhoneNumber phoneNumber = phoneNumberMapper.map(phoneNumberDTO);
        phoneNumber = phoneNumbersRepository.save(phoneNumber);
        return phoneNumber;
    }

    @Override
    public void update(PhoneNumberDTO phoneNumberDTO) throws NotFoundException {
        if (phoneNumbersRepository.exitsById(phoneNumberDTO.getId())) {
            PhoneNumber phoneNumber = phoneNumberMapper.map(phoneNumberDTO);
            phoneNumbersRepository.update(phoneNumber);
        } else {
            throw new NotFoundException("PhoneNumber not found.");
        }
    }

    @Override
    public PhoneNumberDTO findById(int phoneNumberId) throws NotFoundException {
//        PhoneNumber phoneNumber = phoneNumbersRepository.findById(phoneNumberId).orElseThrow(() ->
//                new NotFoundException("PhoneNumber not found."));
//        return phoneNumberDtoMapper.map(phoneNumber);
        return null;
    }

    @Override
    public List<PhoneNumberDTO> findAll() {
//        List<PhoneNumber> phoneNumberList = phoneNumbersRepository.findAll();
//        return phoneNumberDtoMapper.map(phoneNumberList);
        return null;
    }

    @Override
    public boolean delete(int phoneNumberId) {
        return phoneNumbersRepository.deleteById(phoneNumberId);
    }
}
