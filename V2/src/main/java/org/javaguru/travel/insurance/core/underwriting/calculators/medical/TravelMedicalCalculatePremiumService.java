package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

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
class TravelMedicalCalculatePremiumService implements TravelRiskPremiumCalculator {
    private final DayCountCalculating dayCountCalculating;
    private final CountryDefaultDayRateCalculating dayRate;
    private final AgeCoefficientCalculating ageCoefficientCalculating;
    private final MedicalRiskLimitLevelCalculating medicalRiskLimitLevelCalculating;

    @Override
    public BigDecimal calculatePremium(AgreementDTO request,  PersonDTO requestPerson) {
        return dayCountCalculating.calculateDayCount(request)
                .multiply(dayRate.findCountryDefaultDayRate(request))
                .multiply(ageCoefficientCalculating.findAgeCoefficient(requestPerson))
                .multiply(medicalRiskLimitLevelCalculating.findMedicalRiskLimitLevel(requestPerson))
                .setScale(2, RoundingMode.HALF_UP);
    }


    @Override
    public String getRiskIc() {
        return "TRAVEL_MEDICAL";
    }
}
