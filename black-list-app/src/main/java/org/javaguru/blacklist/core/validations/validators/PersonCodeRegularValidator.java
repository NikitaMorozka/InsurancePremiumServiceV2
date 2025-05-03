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
public class PersonCodeRegularValidator implements BlackListedPersonValidator{

    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(BlackListedPersonDTO personDTO) {
        if (!isPersonCodeNullOrBlank(personDTO) && !isValidFormat(personDTO)) {
            Placeholder placeholder = new Placeholder("PERSONAL_CODE", personDTO.getPersonCode());
            return Optional.of(errorFactory.buildError("ERROR_CODE_6", List.of(placeholder)));
        } else {
            return Optional.empty();
        }
    }

    private boolean isPersonCodeNullOrBlank(BlackListedPersonDTO personDTO) {
        return personDTO.getPersonCode() == null || personDTO.getPersonCode().isBlank();
    }

    private boolean isValidFormat(BlackListedPersonDTO personDTO) {
        Pattern pattern = Pattern.compile("^\\d{3}-\\d{3}$");
        Matcher matcher = pattern.matcher(personDTO.getPersonCode());
        return matcher.matches();
    }
}
