package org.javaguru.travel.insurance.core.validations.person;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PersonFirstNameValidator implements ValidationPersonOptional {
    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationErrorDTO> validationOptional(AgreementDTO agreementDTO, PersonDTO request) {
        return ((request.getPersonFirstName() == null) || (request.getPersonFirstName().isEmpty()))
                ? Optional.of(errorsHandler.processing("ERROR_CODE_1"))
                : Optional.empty();
    }
}
