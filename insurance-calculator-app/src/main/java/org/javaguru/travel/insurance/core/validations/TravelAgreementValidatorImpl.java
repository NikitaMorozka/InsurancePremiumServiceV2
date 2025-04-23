package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelAgreementValidatorImpl implements TravelAgreementValidator {

    private final TravelPersonDTOValidation personValidator;

    private final TravelAgreementDTOValidator agreementValidator;

    @Override
    public List<ValidationErrorDTO> validate(AgreementDTO agreementRequest) {
        List<ValidationErrorDTO> person = personValidator.validate(agreementRequest);
        List<ValidationErrorDTO> agreement = agreementValidator.validate(agreementRequest);
        return mergeValidationErrors(person, agreement);
    }


    private List<ValidationErrorDTO> mergeValidationErrors(List<ValidationErrorDTO>... errorsLists) {
        return Arrays.stream(errorsLists)
                .flatMap(Collection::stream)
                .toList();
    }

}
