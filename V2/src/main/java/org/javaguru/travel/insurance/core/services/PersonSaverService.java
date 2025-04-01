package org.javaguru.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.entities.Person;
import org.javaguru.travel.insurance.core.repositories.entities.PersonRepository;
import org.javaguru.travel.insurance.core.repositories.entities.PersonRisksRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PersonSaverService {
    private final PersonRepository personRepository;


    Person createAndSavePerson(PersonDTO personDTO){
        Optional<Person> personOptional =
                personRepository.findBy(
                        personDTO.getPersonFirstName(),
                        personDTO.getPersonLastName(),
                        personDTO.getPersonCode());

        return personOptional.orElseGet(() -> createNewPerson(personDTO));
    }

    private Person createNewPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setFirstName(personDTO.getPersonFirstName());
        person.setLastName(personDTO.getPersonLastName());
        person.setPersonCode(personDTO.getPersonCode());
        person.setBirthDate(personDTO.getPersonBirthDate());
        return personRepository.save(person);
    }

}
