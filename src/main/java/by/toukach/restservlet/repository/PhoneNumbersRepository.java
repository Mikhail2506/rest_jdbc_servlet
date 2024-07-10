package by.toukach.restservlet.repository;

import by.toukach.restservlet.entity.PhoneNumber;

import java.util.List;
import java.util.Optional;

public interface PhoneNumbersRepository {

    boolean deleteById(int id);

    boolean exists(String number);

    PhoneNumber save(PhoneNumber phoneNumber);

    void update(PhoneNumber phoneNumber);

    Optional<PhoneNumber> findByNumber(String number);

    List<PhoneNumber> findAllByPersonId(int id);
}
