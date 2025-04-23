package org.javaguru.travel.insurance.core.services.premium;


import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.domain.entities.Agreement;
import org.javaguru.travel.insurance.core.validations.TravelAgreementValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumServiceImplTest {

    @Mock TravelAgreementValidator agreementValidator;
    @Mock CalculateRisksForPremium calculateRisksForPremium;
    @Mock CalculateTotalPremiumAgreement totalPremiumAgreement;
    @Mock AgreementSaverService agreementSaverService;

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
        assertEquals(result.getErrors().getFirst().errorCode(), "Error code");
        assertEquals(result.getErrors().getFirst().description(), "Error description");
        verifyNoInteractions(totalPremiumAgreement, calculateRisksForPremium);
    }

//
    @Test
    void shouldCalculatePersonsPremium() {
        var command = new TravelCalculatePremiumCoreCommand(new AgreementDTO());
        var savedAgreement = new Agreement();

        savedAgreement.setUuid(UUID.randomUUID());
        when(agreementSaverService.save(any(AgreementDTO.class))).thenReturn(savedAgreement);
        when(agreementValidator.validate(any(AgreementDTO.class))).thenReturn(Collections.emptyList());

        travelCalculatePremiumService.calculatePremium(command);
        verify(totalPremiumAgreement).calculateTotalAgreementPremium(command.getAgreement());

    }


    @Test
    void shouldCalculateAgreementTotalPremium() {
        var command = new TravelCalculatePremiumCoreCommand(new AgreementDTO());
        var savedAgreement = new Agreement();

        savedAgreement.setUuid(UUID.randomUUID());

        when(agreementSaverService.save(any(AgreementDTO.class))).thenReturn(savedAgreement);
        when(agreementValidator.validate(any(AgreementDTO.class))).thenReturn(Collections.emptyList());
        when(totalPremiumAgreement.calculateTotalAgreementPremium(any(AgreementDTO.class))).thenReturn(BigDecimal.ONE);

        TravelCalculatePremiumCoreResult result = travelCalculatePremiumService.calculatePremium(command);

        assertEquals(BigDecimal.ONE, result.getAgreement().getAgreementPremium());
    }
}
