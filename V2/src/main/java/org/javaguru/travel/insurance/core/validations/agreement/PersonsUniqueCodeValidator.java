package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class PersonsUniqueCodeValidator implements ValidationAgreementOptional {

    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationErrorDTO> validationOptional(AgreementDTO request) {
        return request.getPersons()
                .stream()
                .map(PersonDTO::getPersonCode)
                .distinct()
                .count() < request.getPersons().size()
                ? Optional.of(errorsHandler.processing("ERROR_CODE_16"))
                : Optional.empty();
    }
}