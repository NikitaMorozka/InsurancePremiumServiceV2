package org.javaguru.travel.insurance.core.validations.person;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)

public class DateOfBirthDayValidator implements ValidationPersonOptional {

    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationErrorDTO> validationOptional(AgreementDTO agreementDTO, PersonDTO request) {
        if (request.getPersonBirthDate() == null) return Optional.of(errorsHandler.processing("ERROR_CODE_11"));

        return request.getPersonBirthDate().isAfter(LocalDate.now())
                ? Optional.of(errorsHandler.processing("ERROR_CODE_12"))
                : Optional.empty();
    }
}

