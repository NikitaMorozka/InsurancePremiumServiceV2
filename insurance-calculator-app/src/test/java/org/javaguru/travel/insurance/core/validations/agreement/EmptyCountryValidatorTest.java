package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyCountryValidatorTest {
    @Mock
    ErrorValidationFactory errorsHandler;
    @Mock
    AgreementDTO request;

    @InjectMocks private EmptyCountryValidator validation;


    @Test
    void shouldReturnNoErrorWhenSelectedRisksContainsTravelMedicalAndCountryIsPresent() {
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getCountry()).thenReturn("SPAIN");

        Optional<ValidationErrorDTO> errorOpt = validation.validationOptional(request);

        assertTrue(errorOpt.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenSelectedRisksContainsTravelMedicalAndCountryIsNull() {
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getCountry()).thenReturn(null);
        when(errorsHandler.processing("ERROR_CODE_10")).thenReturn(new ValidationErrorDTO("ERROR_CODE_10", "Country not in DataBase"));

        Optional<ValidationErrorDTO> errorOpt = validation.validationOptional(request);

        assertTrue(errorOpt.isPresent());
        assertEquals("ERROR_CODE_10", errorOpt.get().errorCode());
        assertEquals("Country not in DataBase", errorOpt.get().description());
    }

    @Test
    void shouldReturnErrorWhenSelectedRisksContainsTravelMedicalAndCountryIsEmpty() {
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getCountry()).thenReturn("");
        when(errorsHandler.processing("ERROR_CODE_10"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_10", "Country not in DataBase"));

        Optional<ValidationErrorDTO> errorOpt = validation.validationOptional(request);

        assertTrue(errorOpt.isPresent());
        assertEquals("ERROR_CODE_10", errorOpt.get().errorCode());
        assertEquals("Country not in DataBase", errorOpt.get().description());
    }
}
