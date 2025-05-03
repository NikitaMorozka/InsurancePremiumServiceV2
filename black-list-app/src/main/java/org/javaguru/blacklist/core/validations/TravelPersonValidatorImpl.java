package org.javaguru.blacklist.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.blacklist.core.api.dto.BlackListedPersonDTO;
import org.javaguru.blacklist.core.api.dto.ValidationErrorDTO;
import org.javaguru.blacklist.core.validations.validators.BlackListedPersonValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelPersonValidatorImpl implements TravelPersonValidator {

    private final List<BlackListedPersonValidator> validationsPerson;

    public List<ValidationErrorDTO> validate(BlackListedPersonDTO personDTO) {
        return validateAgreementOptionals(personDTO);
    }

    private List<ValidationErrorDTO> validateAgreementOptionals(BlackListedPersonDTO request) {
        return validationsPerson.stream()
                .map(validation -> validation.validate(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }




}
