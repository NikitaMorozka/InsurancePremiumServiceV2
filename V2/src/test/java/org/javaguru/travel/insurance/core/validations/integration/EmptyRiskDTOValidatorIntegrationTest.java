package org.javaguru.travel.insurance.core.validations.integration;

import org.javaguru.travel.insurance.core.api.dto.*;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.javaguru.travel.insurance.core.validations.agreement.EmptyRisksValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)

class EmptyRiskDTOValidatorIntegrationTest {

    @MockBean private ErrorValidationFactory errorsHandler;

    @Autowired
    EmptyRisksValidator emptyRisksValidatorTest;

    @Test
    @DisplayName("Тест: список рисков пуст")
    void shouldReturnValidationErrorWhenSelectedRisksIsEmpty() {
        when(errorsHandler.processing("ERROR_CODE_7"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_7", "Must not be empty!"));

        PersonDTO person = PersonDTOBuilder
                .createPerson()
                .withPersonFirstName("Никита")
                .withPersonLastName("Морозов")
                .withPersonBirthDate(createDate("25.11.2002"))
                .withRisks(null)
                .withMedicalRiskLimitLevel("LEVEL_20000")
                .build();

        AgreementDTO agreement = AgreementDTOBuilder.createAgreement()
                .withDateFrom(createDate("01.01.2020"))
                .withDateTo(createDate("01.01.2030"))
                .withCountry("SPAIN")
                .withSelectedRisk("")
                .withPersons(List.of(person))
                .build();

        Optional<ValidationErrorDTO> validationError = emptyRisksValidatorTest.validationOptional(agreement);

        assertTrue(validationError.isPresent());
        assertEquals("ERROR_CODE_7", validationError.get().errorCode());
        assertEquals("Must not be empty!", validationError.get().description());
    }


    @Test
    @DisplayName("Тест: список рисков имеет пустое значение")
    void shouldReturnValidationErrorRisksIsEmpty(){
        when(errorsHandler.processing("ERROR_CODE_7"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_7", "Must not be empty!"));

        PersonDTO person = PersonDTOBuilder
                .createPerson()
                .withPersonFirstName("Никита")
                .withPersonLastName("Морозов")
                .withPersonBirthDate(createDate("25.11.2002"))
                .withRisks(null)
                .withMedicalRiskLimitLevel("LEVEL_20000")
                .build();

        AgreementDTO agreement = AgreementDTOBuilder.createAgreement()
                .withDateFrom(createDate("01.01.2020"))
                .withDateTo(createDate("01.01.2030"))
                .withCountry("SPAIN")
                .withSelectedRisk(List.of("TRAVEL_MEDICAL","","TRAVEL_LOSS_BAGGAGE"))
                .withPersons(List.of(person))
                .build();

        Optional<ValidationErrorDTO> validationError = emptyRisksValidatorTest.validationOptional(agreement);

        assertTrue(validationError.isPresent());
        assertEquals("ERROR_CODE_7", validationError.get().errorCode());
        assertEquals("Must not be empty!", validationError.get().description());
    }

    private LocalDate createDate(String dateStr) {
        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            date =  LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Некорректный формат даты: " + dateStr, e);
        }
        return date;
    }
}
