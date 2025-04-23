package org.javaguru.travel.insurance.core.validations.person;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PersonLastNameValidatorRegular implements ValidationPersonOptional {
    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationErrorDTO> validationOptional(AgreementDTO agreementDTO, PersonDTO request) {
        if (!validationIsNull(request) && !isValidFormat(request)) {
            Placeholder placeholder = new Placeholder("LAST_NAME", request.getPersonLastName());
            return Optional.of(errorsHandler.processing("ERROR_CODE_23", List.of(placeholder)));
        } else {
            return Optional.empty();
        }
    }

    private boolean validationIsNull(PersonDTO request) {
        return (request.getPersonLastName() == null) || (request.getPersonLastName().isEmpty());
    }

    private boolean isValidFormat(PersonDTO request) {
        Pattern pattern = Pattern.compile("^[а-яА-Я -]+$");
        //        Pattern pattern = Pattern.compile("^[a-zA-Z -]+$");
        Matcher matcher = pattern.matcher(request.getPersonLastName());
        return matcher.matches();
    }
}
