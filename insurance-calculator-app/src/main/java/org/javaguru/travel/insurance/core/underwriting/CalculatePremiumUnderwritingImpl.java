package org.javaguru.travel.insurance.core.underwriting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CalculatePremiumUnderwritingImpl implements CalculatePremiumUnderwriting {
    private final SelectedRisksPremiumCalculator risks;

    @Override
    public TravelPremiumCalculationResult calculateUnderwriting(AgreementDTO agreement, PersonDTO person){
       List<RiskDTO> risksList = calculatePremiumForRisk(agreement,person);
       BigDecimal totalPremium = getTravelPremium(risksList);
       return new TravelPremiumCalculationResult(risksList, totalPremium);
    }

    private List<RiskDTO> calculatePremiumForRisk(AgreementDTO agreement, PersonDTO person){
        return risks.calculatePremiumForRisk(agreement,person);
    }

    private BigDecimal getTravelPremium(List<RiskDTO> listRisks){
        return listRisks.stream().map(RiskDTO::getPremium).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
