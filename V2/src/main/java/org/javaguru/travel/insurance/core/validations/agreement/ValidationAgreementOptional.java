package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.Optional;

public interface ValidationAgreementOptional {
    Optional<ValidationErrorDTO> validationOptional(AgreementDTO request);
}
