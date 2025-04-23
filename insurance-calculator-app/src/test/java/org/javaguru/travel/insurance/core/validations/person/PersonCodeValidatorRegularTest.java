package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.Placeholder;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonCodeValidatorRegularTest {
    @Mock private PersonDTO request;
    @Mock private AgreementDTO agreementDTO;
    @Mock private ErrorValidationFactory errorsHandler;
    @Mock private Placeholder placeholder;
    @InjectMocks
    private PersonCodeValidatorRegular personCodeValidatorRegular;

    @Test
    @DisplayName("Тест: поле должно быть заполнено")
    void shouldReturnErrorWhenPersonCodeNotFormat() {
        when(request.getPersonCode()).thenReturn("111-11");
        when(errorsHandler.processing(eq("ERROR_CODE_21"), any(List.class)))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_21", "Field personCode is empty!"));
        Optional<ValidationErrorDTO> validateErrors = personCodeValidatorRegular.validationOptional(agreementDTO, request);
        assertTrue(validateErrors.isPresent());
        assertEquals("ERROR_CODE_21", validateErrors.get().errorCode());
        assertEquals("Field personCode is empty!", validateErrors.get().description());
    }

    @Test
    @DisplayName("Тест: поле должно быть заполнено")
    void shouldNotReturnErrorWhenPersonCodeNotFormat() {
        when(request.getPersonCode()).thenReturn("111-111");
        Optional<ValidationErrorDTO> validateErrors = personCodeValidatorRegular.validationOptional(agreementDTO, request);
        assertTrue(validateErrors.isEmpty());
    }



}


