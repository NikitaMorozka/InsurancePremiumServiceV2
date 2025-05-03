package org.javaguru.blacklist.core.validations.validators;

import org.javaguru.blacklist.core.api.dto.BlackListedPersonDTO;
import org.javaguru.blacklist.core.api.dto.ValidationErrorDTO;

import java.util.Optional;

public interface BlackListedPersonValidator {

    Optional<ValidationErrorDTO> validate(BlackListedPersonDTO personDTO);

}
