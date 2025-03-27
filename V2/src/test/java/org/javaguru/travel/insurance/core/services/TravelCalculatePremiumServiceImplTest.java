package org.javaguru.travel.insurance.core.services;


import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.TravelAgreementValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumServiceImplTest {

    @Mock
    CalculateRisksForPremium calculateRisksForPremium;
    @Mock
    CalculateTotalPremiumAgreement totalPremiumAgreement;
    @Mock
    TravelCalculatePremiumCoreCommand command;
    @Mock
    TravelAgreementValidator agreementValidator;


    @InjectMocks
    TravelCalculatePremiumServiceImpl travelCalculatePremiumService;

    @Test
    void Test() {
        var agreement = new AgreementDTO();
        var command = new TravelCalculatePremiumCoreCommand(agreement);
        var validationError = new ValidationErrorDTO("Error code", "Error description");
        when(agreementValidator.validate(agreement)).thenReturn(List.of(validationError));

        TravelCalculatePremiumCoreResult result = travelCalculatePremiumService.calculatePremium(command);


        assertEquals(result.getErrors().size(), 1);

        assertEquals(result.getErrors().get(0).errorCode(), "Error code");
        assertEquals(result.getErrors().get(0).description(), "Error description");
        verifyNoInteractions(totalPremiumAgreement, calculateRisksForPremium);
    }


    @Test
    void shouldCalculatePersonsPremium() {
        var agreement = new AgreementDTO();
        when(agreementValidator.validate(agreement)).thenReturn(Collections.emptyList());
        travelCalculatePremiumService.calculatePremium(new TravelCalculatePremiumCoreCommand(agreement));

        verify(totalPremiumAgreement).calculateTotalAgreementPremium(agreement);

    }

    @Test
    void shouldCalculateAgreementTotalPremium() {
        var agreement = new AgreementDTO();
        when(agreementValidator.validate(agreement)).thenReturn(Collections.emptyList());
        when(totalPremiumAgreement.calculateTotalAgreementPremium(agreement)).thenReturn(BigDecimal.ONE);
        TravelCalculatePremiumCoreResult result = travelCalculatePremiumService.calculatePremium(new TravelCalculatePremiumCoreCommand(agreement));
        assertEquals(result.getAgreement().getAgreementPremium(), BigDecimal.ONE);
    }
}
