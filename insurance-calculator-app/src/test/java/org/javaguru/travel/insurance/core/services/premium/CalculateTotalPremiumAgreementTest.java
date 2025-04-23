package org.javaguru.travel.insurance.core.services.premium;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CalculateTotalPremiumAgreementTest {
    private CalculateTotalPremiumAgreement calculateTotalPremiumAgreement = new CalculateTotalPremiumAgreement();

    @Test
    void shouldSumTotalPremiumForAllPersons(){
        RiskDTO risk1 = new RiskDTO("RISK_1", BigDecimal.ONE);
        RiskDTO risk2 = new RiskDTO("RISK_2", BigDecimal.ONE);
        RiskDTO risk3 = new RiskDTO("RISK_1", BigDecimal.ONE);
        RiskDTO risk4 = new RiskDTO("RISK_2", BigDecimal.ONE);

        PersonDTO person1 = new PersonDTO();
        person1.setRisks(List.of(risk1,risk2));
        PersonDTO person2 = new PersonDTO();
        person2.setRisks(List.of(risk3,risk4));

        AgreementDTO agreementDTO = new AgreementDTO();
        agreementDTO.setPersons(List.of(person1,person2));

        BigDecimal premium = calculateTotalPremiumAgreement.calculateTotalAgreementPremium(agreementDTO);
        assertEquals(premium, new BigDecimal(4L));

    }


}
