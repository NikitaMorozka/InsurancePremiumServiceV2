package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.cancellation.TravelCountrySafetyRatingCoefficient;
import org.javaguru.travel.insurance.core.repositories.TravelCountrySafetyRatingCoefficientRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Component
public class TravelCountrySafetyRatingCoefficientCalculator {
    private final TravelCountrySafetyRatingCoefficientRepository coefficientRepository;

    public BigDecimal getSafetyRatingCoefficient(AgreementDTO agreementDTO){
        return  coefficientRepository.findByCountryIc(agreementDTO.getCountry())
                .map(TravelCountrySafetyRatingCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Country not found  = " + agreementDTO.getCountry()));
    }
}
