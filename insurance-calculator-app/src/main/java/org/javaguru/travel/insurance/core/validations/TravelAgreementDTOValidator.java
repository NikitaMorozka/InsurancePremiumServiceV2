package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.agreement.ValidationAgreementList;
import org.javaguru.travel.insurance.core.validations.agreement.ValidationAgreementOptional;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelAgreementDTOValidator {

    private final List<ValidationAgreementList> validationAgreementLists;
    private final List<ValidationAgreementOptional> validationAgreementOptionals;

    public List<ValidationErrorDTO> validate(AgreementDTO agreementDTO) {
        List<ValidationErrorDTO> agreementOptionalErrors = validateAgreementOptionals(agreementDTO);
        List<ValidationErrorDTO> agreementListErrors = validateAgreementLists(agreementDTO);
        return mergeValidationErrors(agreementOptionalErrors, agreementListErrors);
    }

    private List<ValidationErrorDTO> validateAgreementOptionals(AgreementDTO request) {
        return validationAgreementOptionals.stream()
                .map(validation -> validation.validationOptional(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private List<ValidationErrorDTO> validateAgreementLists(AgreementDTO request) {
        return validationAgreementLists.stream()
                .map(validation -> validation.validationList(request))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .toList();
    }

    private List<ValidationErrorDTO> mergeValidationErrors(List<ValidationErrorDTO>... errorsLists) {
        return Arrays.stream(errorsLists)
                .flatMap(Collection::stream)
                .toList();
    }
}
