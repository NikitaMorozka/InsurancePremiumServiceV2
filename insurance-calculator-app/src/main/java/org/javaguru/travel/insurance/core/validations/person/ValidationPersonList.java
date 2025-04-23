package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.List;

public interface ValidationPersonList {
    List<ValidationErrorDTO> validationList(AgreementDTO agreementDTO, PersonDTO request);
}
