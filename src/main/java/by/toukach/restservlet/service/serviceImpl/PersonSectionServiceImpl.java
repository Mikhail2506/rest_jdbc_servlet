package by.toukach.restservlet.service.serviceImpl;

import by.toukach.restservlet.dto.PersonSectionDTO;
import by.toukach.restservlet.entity.PersonSection;
import by.toukach.restservlet.entity.PersonToSection;
import by.toukach.restservlet.exception.NotFoundException;
import by.toukach.restservlet.mapper.MapperPersonSection;
import by.toukach.restservlet.mapper.PersonSectionMapper;
import by.toukach.restservlet.repository.PersonRepository;
import by.toukach.restservlet.repository.PersonSectionsRepository;
import by.toukach.restservlet.repository.PersonToSectionRepository;
import by.toukach.restservlet.repository.repositoryImpl.PersonRepositoryImpl;
import by.toukach.restservlet.repository.repositoryImpl.PersonSectionsRepositoryImpl;
import by.toukach.restservlet.repository.repositoryImpl.PersonToSectionRepositoryImpl;
import by.toukach.restservlet.service.PersonSectionService;

import java.util.List;

public class PersonSectionServiceImpl implements PersonSectionService {

    MapperPersonSection personSectionMapper = new PersonSectionMapper();
    PersonSectionsRepository personSectionsRepository = PersonSectionsRepositoryImpl.getInstance();
    PersonRepository personRepository = PersonRepositoryImpl.getInstance();
    PersonToSectionRepository personToSectionRepository = PersonToSectionRepositoryImpl.getInstance();

    @Override
    public PersonSection save(PersonSectionDTO personSectionDTO) throws NotFoundException {
        PersonSection personSection = personSectionMapper.dtoToEntity(personSectionDTO);
        checkExistPersonSection(personSection.getId());
        personSectionsRepository.save(personSection);
        return personSection;
    }

    private void checkExistPersonSection(Long personSectionId) throws NotFoundException {
        if (!personSectionsRepository.exitsById(personSectionId)) {
            throw new NotFoundException("Department not found.");
        }
    }

    @Override
    public void update(PersonSectionDTO personSectionDTO) throws NotFoundException {
        PersonSection personSection = personSectionMapper.dtoToEntity(personSectionDTO);
        checkExistPersonSection(personSection.getId());
        personSectionsRepository.update(personSection);
    }

    @Override
    public PersonSectionDTO findById(Long personSectionId) throws NotFoundException {
        PersonSection personSection = personSectionsRepository.findById(personSectionId).orElseThrow(() ->
                new NotFoundException("Department not found."));
        return personSectionMapper.entityToDto(personSection);
    }

    @Override
    public List<PersonSectionDTO> findAll() {
        List<PersonSection> personSectionList = personSectionsRepository.findAll();
        return personSectionMapper.entityToDtoList(personSectionList);
    }

    @Override
    public void delete(Long personSectionId) throws NotFoundException {
        checkExistPersonSection(personSectionId);
        personSectionsRepository.deleteById(personSectionId);
    }

    @Override
    public void deletePersonFromSection(Long personSectionId, Long personId) throws NotFoundException {
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
    public void addPersonToPersonSection(Long personSectionId, Long personId) throws NotFoundException {
        checkExistPersonSection(personSectionId);
        if (personRepository.existById(personId)) {
            PersonToSection personToSection = new PersonToSection(
                    null,
                    personId,
                    personSectionId
            );
            personToSectionRepository.save(personToSection);
        } else {
            throw new NotFoundException("User not found.");
        }
    }
}
