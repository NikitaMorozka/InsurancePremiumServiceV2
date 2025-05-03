package org.javaguru.blacklist.core.validations;


import org.javaguru.blacklist.core.api.dto.BlackListedPersonDTO;
import org.javaguru.blacklist.core.api.dto.ValidationErrorDTO;

import java.util.List;

public interface TravelPersonValidator {
    List<ValidationErrorDTO> validate(BlackListedPersonDTO personDTO);
}
