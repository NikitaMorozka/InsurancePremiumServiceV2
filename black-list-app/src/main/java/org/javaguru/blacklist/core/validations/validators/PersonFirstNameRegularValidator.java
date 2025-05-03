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
public class PersonFirstNameRegularValidator implements BlackListedPersonValidator {

    private final ValidationErrorFactory errorFactory;


    @Override
    public Optional<ValidationErrorDTO> validate(BlackListedPersonDTO request) {
        if (!validationIsNull(request) && !isValidFormat(request)) {
            Placeholder placeholder = new Placeholder("FIRST_NAME", request.getPersonFirstName());
            return Optional.of(errorFactory.buildError("ERROR_CODE_4", List.of(placeholder)));
        } else {
            return Optional.empty();
        }
    }

    private boolean validationIsNull(BlackListedPersonDTO request) {
        return (request.getPersonFirstName() == null) || (request.getPersonFirstName().isEmpty());
    }

    private boolean isValidFormat(BlackListedPersonDTO request) {
        Pattern pattern = Pattern.compile("^[а-яА-Я -]+$");
        //        Pattern pattern = Pattern.compile("^[a-zA-Z -]+$");
        Matcher matcher = pattern.matcher(request.getPersonFirstName());
        return matcher.matches();
    }
}
