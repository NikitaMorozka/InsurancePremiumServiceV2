package org.javaguru.blacklist.core.validations.validators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.blacklist.core.api.dto.BlackListedPersonDTO;
import org.javaguru.blacklist.core.api.dto.ValidationErrorDTO;
import org.javaguru.blacklist.core.util.Placeholder;
import org.javaguru.blacklist.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PersonLastNameRegularValidator implements BlackListedPersonValidator {
    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(BlackListedPersonDTO personDTO) {
        if (!validationIsNull(personDTO) && !isValidFormat(personDTO)) {
            Placeholder placeholder = new Placeholder("LAST_NAME", personDTO.getPersonLastName());
            return Optional.of(errorFactory.buildError("ERROR_CODE_5", List.of(placeholder)));
        } else {
            return Optional.empty();
        }
    }

    private boolean validationIsNull(BlackListedPersonDTO personDTO) {
        return (personDTO.getPersonLastName() == null) || (personDTO.getPersonLastName().isEmpty());
    }

    private boolean isValidFormat(BlackListedPersonDTO personDTO) {
        Pattern pattern = Pattern.compile("^[а-яА-Я -]+$");
        //        Pattern pattern = Pattern.compile("^[a-zA-Z -]+$");
        Matcher matcher = pattern.matcher(personDTO.getPersonLastName());
        return matcher.matches();
    }
}
