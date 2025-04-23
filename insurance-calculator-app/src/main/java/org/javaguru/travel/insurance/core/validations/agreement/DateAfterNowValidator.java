package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DateAfterNowValidator implements ValidationAgreementOptional {
    private final ErrorValidationFactory errorsHandler;

    private LocalDate getCurrentDate() {
        ZoneId zoneId = ZoneId.of("UTC");
        return ZonedDateTime.now(zoneId).toLocalDate();
    }

    @Override
    public Optional<ValidationErrorDTO> validationOptional(AgreementDTO request) {
        return check(request.getAgreementDateFrom(), request.getAgreementDateTo())
                ? Optional.of(errorsHandler.processing("ERROR_CODE_6"))
                : Optional.empty();
    }

    private boolean check(LocalDate dateFrom, LocalDate dateTo) {
        LocalDate currentDate = getCurrentDate();
        return (dateFrom != null && dateTo != null)
                && (dateTo.isBefore(currentDate) || dateFrom.isBefore(currentDate));
    }
}
