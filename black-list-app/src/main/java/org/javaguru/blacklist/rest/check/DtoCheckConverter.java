package org.javaguru.blacklist.rest.check;

import org.javaguru.blacklist.core.api.command.BlackListedPersonCoreCommand;
import org.javaguru.blacklist.core.api.command.BlackListedPersonCoreResult;
import org.javaguru.blacklist.core.api.dto.BlackListedPersonDTO;
import org.javaguru.blacklist.core.api.dto.ValidationErrorDTO;
import org.javaguru.blacklist.dto.check.BlackListedPersonCheckRequest;
import org.javaguru.blacklist.dto.check.BlackListedPersonCheckResponse;
import org.javaguru.blacklist.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoCheckConverter {

    public BlackListedPersonCoreCommand buildCoreCommand(BlackListedPersonCheckRequest request) {
        BlackListedPersonDTO person = buildPerson(request);
        return new BlackListedPersonCoreCommand(person);
    }

    private BlackListedPersonDTO buildPerson(BlackListedPersonCheckRequest request) {
        BlackListedPersonDTO person = new BlackListedPersonDTO();
        person.setPersonFirstName(request.getPersonFirstName());
        person.setPersonLastName(request.getPersonLastName());
        person.setPersonCode(request.getPersonCode());
        return person;
    }

    public BlackListedPersonCheckResponse buildResponse(BlackListedPersonCoreResult coreResult) {
        return coreResult.hasErrors()
                ? buildResponseWithErrors(coreResult.getErrors())
                : buildSuccessfulResponse(coreResult);
    }

    private BlackListedPersonCheckResponse buildResponseWithErrors(List<ValidationErrorDTO> coreErrors) {
        List<ValidationError> errors = transformValidationErrors(coreErrors);
        return new BlackListedPersonCheckResponse(errors);
    }

    private List<ValidationError> transformValidationErrors(List<ValidationErrorDTO> coreErrors) {
        return coreErrors.stream()
                .map(error -> new ValidationError(error.getErrorCode(), error.getDescription()))
                .collect(Collectors.toList());
    }

    private BlackListedPersonCheckResponse buildSuccessfulResponse(BlackListedPersonCoreResult coreResult) {
        BlackListedPersonDTO person = coreResult.getPersonDTO();
        BlackListedPersonCheckResponse response = new BlackListedPersonCheckResponse();
        response.setPersonFirstName(person.getPersonFirstName());
        response.setPersonLastName(person.getPersonLastName());
        response.setPersonCode(person.getPersonCode());
        response.setBlacklisted(person.getBlackListed());
        return response;
    }

}
