package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SelectedRisksPremiumCalculatorTest {

    @Mock private TravelRiskPremiumCalculator premium1;
    @Mock private TravelRiskPremiumCalculator premium2;
    @Mock private AgreementDTO agreement;
    @Mock private PersonDTO person;

    @InjectMocks
    private SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;

    @BeforeEach
    void setUp() {
        var riskPremiumCalculators = List.of(premium1, premium2);
        selectedRisksPremiumCalculator = new SelectedRisksPremiumCalculator(riskPremiumCalculators);
    }

    @Test
    void shouldCalculatePremiumForOneRisk() {
        when(premium1.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
        when(premium1.calculatePremium(agreement, person)).thenReturn(new BigDecimal(2L));
        when(agreement.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));

        List<RiskDTO> risksList = selectedRisksPremiumCalculator.calculatePremiumForRisk(agreement, person);

        assertEquals(new BigDecimal(2L), risksList.getFirst().getPremium());
        assertEquals("TRAVEL_MEDICAL", risksList.getFirst().getRiskIc());
        assertEquals(1, risksList.size());
    }

    @Test
    void shouldCalculatePremiumForTwoRisk() {
        when(premium1.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
        when(premium1.calculatePremium(agreement, person)).thenReturn(new BigDecimal(2L));
        when(premium2.getRiskIc()).thenReturn("TRAVEL_EVACUATION");
        when(premium2.calculatePremium(agreement, person)).thenReturn(new BigDecimal(2L));
        when(agreement.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL", "TRAVEL_EVACUATION"));

        List<RiskDTO> risksList = selectedRisksPremiumCalculator.calculatePremiumForRisk(agreement, person);

        assertEquals(new BigDecimal(2L), risksList.getLast().getPremium());
        assertEquals("TRAVEL_EVACUATION", risksList.getLast().getRiskIc());
        assertEquals(new BigDecimal(2L), risksList.getFirst().getPremium());
        assertEquals("TRAVEL_MEDICAL", risksList.getFirst().getRiskIc());
        assertEquals(2, risksList.size());
    }

    @Test
    void shouldThrowExceptionWhenSelectedRiskTypeNotSupported() {
        when(premium1.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
        when(premium2.getRiskIc()).thenReturn("TRAVEL_EVACUATION");
        when(agreement.getSelectedRisks()).thenReturn(List.of("NOT_SUPPORTED_RISK_TYPE"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> selectedRisksPremiumCalculator.calculatePremiumForRisk(agreement, person));

        assertEquals("Not supported: NOT_SUPPORTED_RISK_TYPE", exception.getMessage());
    }
}