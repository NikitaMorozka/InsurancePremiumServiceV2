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

public class SelectedRisksPremiumCalculator {

    private final List<TravelRiskPremiumCalculator> listRisks;

    public List<RiskDTO> calculatePremiumForRisk(AgreementDTO agreement, PersonDTO person) {
        return agreement.getSelectedRisks().stream()
                .map(risk -> new RiskDTO(risk, calculatePremiumForRisk(risk, agreement, person)))
                .toList();
    }

    private BigDecimal calculatePremiumForRisk(String risk, AgreementDTO agreement, PersonDTO person){
        return getTravelPremium(risk).calculatePremium(agreement, person);
    }

    private TravelRiskPremiumCalculator getTravelPremium(String riskIc){
        return listRisks.stream()
                .filter(risk -> risk.getRiskIc().equals(riskIc))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not supported: " + riskIc));
    }
}
