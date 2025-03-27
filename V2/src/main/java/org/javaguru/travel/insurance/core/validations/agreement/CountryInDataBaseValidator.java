package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class CountryInDataBaseValidator implements ValidationAgreementOptional {

    private final ErrorValidationFactory errorsHandler;
    private final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Override
    public Optional<ValidationErrorDTO> validationOptional(AgreementDTO request) {
        return (request.getCountry() != null && !request.getCountry().isBlank() &&
                countryDefaultDayRateRepository.findDefaultDayRateByCountryIc(request.getCountry()).isEmpty())
                ? Optional.of(errorsHandler.processing("ERROR_CODE_9"))
                : Optional.empty();
    }
}