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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateFromValidatorTest {

    @Mock private AgreementDTO request;
    @Mock private ErrorValidationFactory errorsHandler;

    @InjectMocks
    DateFromValidator dateFromValidator;

    @Test
    @DisplayName("Тест: правильно заполненные поля")
    void shouldNotReturnErrorForValidDateFrom() {
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.of(2025, 1, 1));

        Optional<ValidationErrorDTO> validationError = dateFromValidator.validationOptional(request);

        assertFalse(validationError.isPresent());
    }

    @Test
    @DisplayName("Тест: поле DateFrom не должно быть null")
    void shouldReturnErrorWhenDateFromIsNull() {
        when(request.getAgreementDateFrom()).thenReturn(null);
        when(errorsHandler.processing("ERROR_CODE_3")).thenReturn(new ValidationErrorDTO("ERROR_CODE_3","DateFrom must not be null!"));

        Optional<ValidationErrorDTO> validationError = dateFromValidator.validationOptional(request);

        assertTrue(validationError.isPresent());
        assertEquals("ERROR_CODE_3", validationError.get().errorCode());
        assertEquals("DateFrom must not be null!", validationError.get().description());
    }

}
