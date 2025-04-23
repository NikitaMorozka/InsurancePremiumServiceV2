package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DateToCompareDateFromValidator implements ValidationAgreementOptional {

    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationErrorDTO> validationOptional(AgreementDTO request) {
        return check(request.getAgreementDateFrom(),request.getAgreementDateTo())
                ? Optional.of(errorsHandler.processing("ERROR_CODE_5"))
                : Optional.empty();

    }

    private boolean check(LocalDate dateFrom, LocalDate dateTo) {
        return (dateFrom != null && dateTo != null)
                && (dateTo.equals(dateFrom) || dateFrom.isAfter(dateTo));
    }
}
