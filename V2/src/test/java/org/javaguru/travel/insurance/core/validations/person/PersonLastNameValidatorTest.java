package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
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


class PersonLastNameValidatorTest {

    @Mock private PersonDTO request;
    @Mock private AgreementDTO agreementDTO;
    @Mock private ErrorValidationFactory errorsHandler;

    @InjectMocks
    PersonLastNameValidator personLastNameValidator;

    @Test
    @DisplayName("Тест: поле должно быть заполнено")
    void shouldNotReturnErrorWhenPersonLastNameIsProvided() {
        when(request.getPersonLastName()).thenReturn("Name");

        Optional<ValidationErrorDTO> validateErrors = personLastNameValidator.validationOptional(agreementDTO, request);

        assertTrue(validateErrors.isEmpty());
    }

    @Test
    @DisplayName("Тест: имя не должно быть пустым (null)")
    void shouldReturnErrorWhenPersonLastNameIsNull() {
        when(request.getPersonLastName()).thenReturn(null);
        when(errorsHandler.processing("ERROR_CODE_2")).thenReturn(new ValidationErrorDTO("ERROR_CODE_2","Field personLastName is empty!"));

        Optional<ValidationErrorDTO> validateErrors = personLastNameValidator.validationOptional(agreementDTO, request);

        assertEquals("ERROR_CODE_2", validateErrors.get().errorCode());
        assertEquals("Field personLastName is empty!", validateErrors.get().description());
        assertTrue(validateErrors.isPresent());
    }

    @Test
    @DisplayName("Тест: имя не должно быть пустым (пустая строка)")
    void shouldReturnErrorWhenPersonLastNameIsEmpty() {
        when(request.getPersonLastName()).thenReturn("");
        when(errorsHandler.processing("ERROR_CODE_2")).thenReturn(new ValidationErrorDTO("ERROR_CODE_2","Field personLastName is empty!"));

        Optional<ValidationErrorDTO> validateErrors = personLastNameValidator.validationOptional(agreementDTO, request);

        assertEquals("ERROR_CODE_2", validateErrors.get().errorCode());
        assertEquals("Field personLastName is empty!", validateErrors.get().description());
        assertTrue(validateErrors.isPresent());
    }

}
