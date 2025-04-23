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
public class PersonCodeValidatorRegular implements ValidationPersonOptional{

    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationErrorDTO> validationOptional(AgreementDTO agreementDTO, PersonDTO request) {
        if (!isPersonCodeNullOrBlank(request) && !isValidFormat(request)) {
            Placeholder placeholder = new Placeholder("PERSONAL_CODE", request.getPersonCode());
            return Optional.of(errorsHandler.processing("ERROR_CODE_21", List.of(placeholder)));
        } else {
            return Optional.empty();
        }
    }

    private boolean isPersonCodeNullOrBlank(PersonDTO person) {
        return person.getPersonCode() == null || person.getPersonCode().isBlank();
    }

    private boolean isValidFormat(PersonDTO person) {
        Pattern pattern = Pattern.compile("^\\d{3}-\\d{3}$");
        Matcher matcher = pattern.matcher(person.getPersonCode());
        return matcher.matches();
    }
}
