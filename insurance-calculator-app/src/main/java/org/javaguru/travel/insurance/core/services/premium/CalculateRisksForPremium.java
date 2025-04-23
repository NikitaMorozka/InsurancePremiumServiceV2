package org.javaguru.travel.insurance.core.services.premium;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.underwriting.CalculatePremiumUnderwriting;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CalculateRisksForPremium {

    private final CalculatePremiumUnderwriting calculatePremium;

    void calculateRiskPremiumsForAllPersons(AgreementDTO agreement) {
        agreement.getPersons().forEach(person -> {
            TravelPremiumCalculationResult calculationResult = calculatePremium.calculateUnderwriting(agreement, person);
            person.setRisks(calculationResult.risks());
        });
    }
}
