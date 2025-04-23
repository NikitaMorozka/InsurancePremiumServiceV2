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

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateToValidatorTest {

    @Mock private AgreementDTO request;
    @Mock private ErrorValidationFactory errorsHandler;

    @InjectMocks
    DateToValidator dateToValidator;

    @Test
    @DisplayName("Тест: правильно заполненные поля")
    void shouldNotReturnErrorForValidDateFrom() {
        when(request.getAgreementDateTo()).thenReturn(LocalDate.of(2025, 1, 1));

        Optional<ValidationErrorDTO> validateErrors = dateToValidator.validationOptional(request);

        assertTrue(validateErrors.isEmpty());
    }

    @Test
    @DisplayName("Тест: поле DateTo не должно быть null")
    void shouldReturnErrorWhenDateToIsNull() {
        when(request.getAgreementDateTo()).thenReturn(null);
        when(errorsHandler.processing("ERROR_CODE_4")).thenReturn(new ValidationErrorDTO("ERROR_CODE_4","DateTo must not be null!"));

        Optional<ValidationErrorDTO> validationError = dateToValidator.validationOptional(request);

        assertTrue(validationError.isPresent());
        assertEquals("ERROR_CODE_4", validationError.get().errorCode());
        assertEquals("DateTo must not be null!", validationError.get().description());
    }

}
