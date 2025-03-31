package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonCodeValidatorTest {

    @Mock private PersonDTO request;
    @Mock private ErrorValidationFactory errorsHandler;

    @InjectMocks
    PersonCodeValidator personCodeValidator;
    @Test
    @DisplayName("Тест: поле персонального кода должно быть заполнено")
    void shouldNotReturnErrorWhenPersonCodeIsProvided() {
        when(request.getPersonCode()).thenReturn("blabla");

        Optional<ValidationErrorDTO> validateErrors = personCodeValidator.validationOptional(request);

        assertTrue(validateErrors.isEmpty());
    }

    @Test
    @DisplayName("Тест: персональный код не должен быть пустым (null)")
    void shouldReturnErrorWhenPersonCodeIsNull() {
        when(request.getPersonCode()).thenReturn(null);
        when(errorsHandler.processing("ERROR_CODE_15")).thenReturn(new ValidationErrorDTO("ERROR_CODE_15", "Field personCode is empty!"));

        Optional<ValidationErrorDTO> validateErrors = personCodeValidator.validationOptional(request);

        assertEquals("ERROR_CODE_15", validateErrors.get().errorCode());
        assertEquals("Field personCode is empty!", validateErrors.get().description());
        assertTrue(validateErrors.isPresent());
    }

    @Test
    @DisplayName("Тест: персональный код не должен быть пустым (пустая строка)")
    void shouldReturnErrorWhenPersonCodeIsEmpty() {
        when(request.getPersonCode()).thenReturn("");
        when(errorsHandler.processing("ERROR_CODE_15")).thenReturn(new ValidationErrorDTO("ERROR_CODE_15", "Field personCode is empty!"));

        Optional<ValidationErrorDTO> validateErrors = personCodeValidator.validationOptional(request);

        assertEquals("ERROR_CODE_15", validateErrors.get().errorCode());
        assertEquals("Field personCode is empty!", validateErrors.get().description());
        assertTrue(validateErrors.isPresent());
    }


}
