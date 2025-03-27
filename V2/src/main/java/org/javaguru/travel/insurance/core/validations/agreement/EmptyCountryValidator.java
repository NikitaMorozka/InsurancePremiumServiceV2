package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class EmptyCountryValidator implements ValidationAgreementOptional {
    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationErrorDTO> validationOptional(AgreementDTO request) {
        return (containsTravel(request) || countryIsNullOrBlank(request))
                ? Optional.of(errorsHandler.processing("ERROR_CODE_10"))
                : Optional.empty();
    }

    private boolean containsTravel(AgreementDTO request) {
        return request.getSelectedRisks() == null;
    }

    private boolean countryIsNullOrBlank(AgreementDTO request) {
        return request.getCountry() == null || request.getCountry().isBlank();
    }

}
