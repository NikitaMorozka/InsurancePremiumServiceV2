package org.javaguru.travel.insurance.core.validations.agreement;

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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonsUniqueCodeValidatorTest {

    @Mock
    ErrorValidationFactory errorsHandler;
    @Mock AgreementDTO agreementDTO;

    @InjectMocks PersonsUniqueCodeValidator personsUniqueCodeValidator;

    @Test
    @DisplayName("Тест: персональные коды уникальны — ошибок нет")
    void shouldNotReturnErrorWhenPersonCodesAreUnique() {
        PersonDTO personDTO1 = PersonDTO.builder()
                .personCode("One")
                .build();

        PersonDTO personDTO2 = PersonDTO.builder()
                .personCode("Two")
                .build();

        when(agreementDTO.getPersons()).thenReturn(List.of(personDTO1, personDTO2));

        Optional<ValidationErrorDTO> validationErrorOpt = personsUniqueCodeValidator.validationOptional(agreementDTO);
        assertTrue(validationErrorOpt.isEmpty());
    }

    @Test
    @DisplayName("Тест: персональные коды не уникальны — возвращается ошибка")
    void shouldReturnErrorWhenPersonCodesAreNotUnique() {
        PersonDTO personDTO1 = PersonDTO.builder()
                .personCode("One")
                .build();

        PersonDTO personDTO2 = PersonDTO.builder()
                .personCode("One")
                .build();

        when(agreementDTO.getPersons()).thenReturn(List.of(personDTO1, personDTO2));
        when(errorsHandler.processing("ERROR_CODE_16")).thenReturn(new ValidationErrorDTO("ERROR_CODE_16", "PersonCode must not be unique!"));
        Optional<ValidationErrorDTO> validationErrorOpt = personsUniqueCodeValidator.validationOptional(agreementDTO);

        assertTrue(validationErrorOpt.isPresent());
        assertEquals("ERROR_CODE_16", validationErrorOpt.get().errorCode());
        assertEquals("PersonCode must not be unique!", validationErrorOpt.get().description());
    }

}