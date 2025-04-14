package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelAgreementValidatorImplTest {

    @Mock private TravelPersonDTOValidation personValidator;
    @Mock private TravelAgreementDTOValidator agreementValidator;
    @Mock private AgreementDTO agreementRequest;

    @InjectMocks
    private TravelAgreementValidatorImpl requestValidator;


    @Test
    @DisplayName("Тест: 4 ошибки валидации (2 ошибки по PersonDTO + 2 ошибки по AgreementDTO)")
    void shouldReturnErrorsForInvalidFields() {
        List<ValidationErrorDTO> personErrors = List.of(new ValidationErrorDTO(), new ValidationErrorDTO());
        List<ValidationErrorDTO> agreementErrors = List.of(new ValidationErrorDTO(), new ValidationErrorDTO());
        when(personValidator.validate(agreementRequest)).thenReturn(personErrors);
        when(agreementValidator.validate(agreementRequest)).thenReturn(agreementErrors);
        List<ValidationErrorDTO> errors = requestValidator.validate(agreementRequest);
        assertEquals(4, errors.size());
    }


    @Test
    @DisplayName("Тест: Если список валидаторов пуст, ошибок не должно быть")
    void shouldReturnNoErrorsWhenNoValidatorsPresent() {
        when(personValidator.validate(agreementRequest)).thenReturn(List.of());
        when(agreementValidator.validate(agreementRequest)).thenReturn(List.of());
        List<ValidationErrorDTO> errors = requestValidator.validate(agreementRequest);
        assertTrue(errors.isEmpty());
    }
}

