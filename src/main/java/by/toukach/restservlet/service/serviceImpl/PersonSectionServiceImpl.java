package by.toukach.restservlet.service.serviceImpl;

import by.toukach.restservlet.dto.PersonSectionDTO;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PersonToSection;
import by.toukach.restservlet.exception.NotFoundException;
import by.toukach.restservlet.mapper.PersonSectionMapper;
import by.toukach.restservlet.mapper.impl.PersonSectionMapperImpl;
import by.toukach.restservlet.repository.PersonRepository;
import by.toukach.restservlet.repository.PersonSectionsRepository;
import by.toukach.restservlet.repository.PersonToSectionRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonRepositoryImpl;
import by.toukach.restservlet.repository.repositoryImpl.PersonSectionsRepositoryImpl;
import by.toukach.restservlet.repository.repositoryImpl.PersonToSectionRepositoryImpl;
import by.toukach.restservlet.service.PersonSectionService;

import java.util.List;

public class PersonSectionServiceImpl implements PersonSectionService {

    private final PersonSectionsRepository personSectionsRepository = PersonSectionsRepositoryImpl.getInstance();
    private final PersonRepository personRepository = PersonRepositoryImpl.getInstance();
    private final PersonToSectionRepository personToSectionRepository = PersonToSectionRepositoryImpl.getInstance();
    private static final PersonSectionMapper personSectionMapper = PersonSectionMapperImpl.getInstance();

    private static PersonSectionService instance;


    private PersonSectionServiceImpl() {
    }

    public static synchronized PersonSectionService getInstance() {
        if (instance == null) {
            instance = new PersonSectionServiceImpl();
        }
        return instance;
    }

    @Override
    public PersonSection save(PersonSectionDTO personSectionDTO) throws NotFoundException {
        PersonSection personSection = personSectionMapper.map(personSectionDTO);
        checkExistPersonSection(personSection.getSectionId());
        personSectionsRepository.save(personSection);
        return personSection;
    }

    private void checkExistPersonSection(int personSectionId) throws NotFoundException {
        if (!personSectionsRepository.exitsById(personSectionId)) {
            throw new NotFoundException("Department not found.");
        }
    }

    @Override
    public void update(PersonSectionDTO personSectionDTO) throws NotFoundException {
        PersonSection personSection = personSectionMapper.map(personSectionDTO);
        checkExistPersonSection(personSection.getSectionId());
        personSectionsRepository.update(personSection);
    }

    @Override
    public PersonSectionDTO findById(int personSectionId) throws NotFoundException {
        PersonSection personSection = personSectionsRepository.findById(personSectionId).orElseThrow(() ->
                new NotFoundException("Section not found."));
        return personSectionMapper.map(personSection);
    }

    @Override
    public List<PersonSectionDTO> findAll() {
        List<PersonSection> personSectionList = personSectionsRepository.findAll();
        return personSectionMapper.map(personSectionList);
    }

    @Override
    public void delete(int personSectionId) throws NotFoundException {
        checkExistPersonSection(personSectionId);
        personSectionsRepository.deleteById(personSectionId);
    }

    @Override
    public void deletePersonFromSection(int personSectionId, int personId) throws NotFoundException {
        checkExistPersonSection(personSectionId);
        if (personRepository.existById(personId)) {
            PersonToSection personToSection = personToSectionRepository.findByPersonIdAndSectiontId(personId, personSectionId)
                    .orElseThrow(() -> new NotFoundException("Link many to many Not found."));
            personToSectionRepository.deleteByPersonId(personToSection.getId());
        } else {
            throw new NotFoundException("User not found.");
        }
    }

    @Override
    public void addPersonToPersonSection(int personSectionId, int personId) throws NotFoundException {
        checkExistPersonSection(personSectionId);
        if (personRepository.existById(personId)) {
            PersonToSection personToSection = new PersonToSection(
                    null,
                    personId,
                    personSectionId
            );
            personToSectionRepository.save(personId, personSectionId);
        } else {
            throw new NotFoundException("User not found.");
        }
    }
}
