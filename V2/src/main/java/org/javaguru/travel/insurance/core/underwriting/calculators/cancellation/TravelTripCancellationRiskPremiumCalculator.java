package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelTripCancellationRiskPremiumCalculator implements TravelRiskPremiumCalculator {
    private final TCAgeCoefficientCalculating tcAgeCoefficientCalculating;
    private final TravelCostCostCoefficientCalculator travelCostCostCoefficientCalculator;
    private final TravelCountrySafetyRatingCoefficientCalculator travelCountrySafetyRatingCoefficientCalculator;

    @Override
    public BigDecimal calculatePremium(AgreementDTO agreement, PersonDTO person) {
        return tcAgeCoefficientCalculating.findAgeCoefficient(person)
                .add(travelCostCostCoefficientCalculator.getCostCoefficient(person))
                .add(travelCountrySafetyRatingCoefficientCalculator.getSafetyRatingCoefficient(agreement))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_CANCELLATION";
    }

}
