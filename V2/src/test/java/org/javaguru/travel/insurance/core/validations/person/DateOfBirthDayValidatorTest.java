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

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateOfBirthDayValidatorTest {

    @Mock private ErrorValidationFactory errorsHandler;
    @Mock private PersonDTO request;

    @InjectMocks
    DateOfBirthDayValidator dateOfBirthDayValidator;

    @Test
    @DisplayName("Тест: Дата рождения раньше настоящего")
    void test1(){
        when(request.getPersonBirthDate()).thenReturn(LocalDate.of(2012, 11 , 25));

        Optional<ValidationErrorDTO> validationError = dateOfBirthDayValidator.validationOptional(request);

        assertTrue(validationError.isEmpty());
    }

    @Test
    @DisplayName("Тест: Дата рождения не должна быть null")
    void test2(){
        when(request.getPersonBirthDate()).thenReturn(null);
        when(errorsHandler.processing("ERROR_CODE_11")).thenReturn(new ValidationErrorDTO("ERROR_CODE_11", "Date of Birth must not be null"));

        Optional<ValidationErrorDTO> validationError = dateOfBirthDayValidator.validationOptional(request);

        assertEquals("ERROR_CODE_11", validationError.get().errorCode());
    }

    @Test
    @DisplayName("Тест: Дата рождения позже настоящего")
    void test3(){
        when(request.getPersonBirthDate()).thenReturn(LocalDate.now().plusDays(7));
        when(errorsHandler.processing("ERROR_CODE_12")).thenReturn(new ValidationErrorDTO("ERROR_CODE_12", "Date of birth must not be in the future!"));

        Optional<ValidationErrorDTO> validationError = dateOfBirthDayValidator.validationOptional(request);

        assertEquals("ERROR_CODE_12", validationError.get().errorCode());
    }

}


