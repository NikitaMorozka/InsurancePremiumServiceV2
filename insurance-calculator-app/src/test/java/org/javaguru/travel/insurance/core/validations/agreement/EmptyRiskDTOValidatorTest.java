package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.junit.jupiter.api.DisplayName;
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
class EmptyRiskDTOValidatorTest {

    @Mock private AgreementDTO request;
    @Mock private ErrorValidationFactory errorsHandler;

    @InjectMocks
    EmptyRisksValidator emptyRisksValidatorTest;

    @Test
    @DisplayName("Тест: список рисков пуст")
    void shouldReturnValidationErrorWhenSelectedRisksIsEmpty(){
        when(request.getSelectedRisks()).thenReturn(List.of());
        when(errorsHandler.processing("ERROR_CODE_7")).thenReturn(new ValidationErrorDTO("ERROR_CODE_7","Must not be empty!"));

        Optional<ValidationErrorDTO> validationError = emptyRisksValidatorTest.validationOptional(request);

        assertEquals("ERROR_CODE_7", validationError.get().errorCode());
        assertEquals("Must not be empty!", validationError.get().description());
    }

    @Test
    @DisplayName("Тест: в список рисков переданы значения")
    void shouldPassValidationWhenSelectedRisksAreProvided(){
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));

        Optional<ValidationErrorDTO> validationError = emptyRisksValidatorTest.validationOptional(request);

        assertTrue(validationError.isEmpty());
    }

    @Test
    @DisplayName("Тест: список рисков имеет пустое значение")
    void shouldReturnValidationErrorRisksIsEmpty(){
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL","","TRAVEL_LOSS_BAGGAGE"));
        when(errorsHandler.processing("ERROR_CODE_7")).thenReturn(new ValidationErrorDTO("ERROR_CODE_7","Must not be empty!"));

        Optional<ValidationErrorDTO> validationError = emptyRisksValidatorTest.validationOptional(request);

        assertEquals("ERROR_CODE_7", validationError.get().errorCode());
        assertEquals("Must not be empty!", validationError.get().description());
    }
}
