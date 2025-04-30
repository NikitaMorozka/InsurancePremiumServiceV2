package org.javaguru.blacklist.rest.all;

import org.javaguru.blacklist.core.api.command.BlackListedPersonDeleteCoreResult;
import org.javaguru.blacklist.core.api.command.BlackListedPersonsListCoreResult;
import org.javaguru.blacklist.core.api.dto.BlackListedPersonDTO;
import org.javaguru.blacklist.dto.getAll.PersonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoAllConverter {

    public List<PersonResponse> build(BlackListedPersonsListCoreResult result) {
        return result.getBlackListedPersons().stream()
                .map(this::buildPersonResponse).toList();
    }

    public List<PersonResponse> build(BlackListedPersonDeleteCoreResult result) {
        return result.getBlackListedPersons().stream()
                .map(this::buildPersonResponse).toList();
    }

    private PersonResponse buildPersonResponse(BlackListedPersonDTO personDTO) {
        return new PersonResponse(
                personDTO.getId(),
                personDTO.getPersonFirstName(),
                personDTO.getPersonLastName(),
                personDTO.getPersonCode(),
                personDTO.getBlackListed()
        );
    }
}
