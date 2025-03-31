package org.javaguru.travel.insurance.core.validations.person;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PersonCodeValidator implements ValidationPersonOptional{

    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationErrorDTO> validationOptional(PersonDTO request) {
        return ((request.getPersonCode() == null) || (request.getPersonCode().isEmpty()))
                ? Optional.of(errorsHandler.processing("ERROR_CODE_15"))
                : Optional.empty();
    }
}
