package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.List;
import java.util.UUID;

public interface TravelAgreementUuidValidator {
    List<ValidationErrorDTO> validate(UUID uuid);
}
