package org.javaguru.travel.insurance.core.validations.integration;

import org.javaguru.travel.insurance.core.api.dto.*;
import org.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.javaguru.travel.insurance.core.validations.person.MedicalRiskLimitLevelInDBValidation;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)

class MedicalRiskLimitLevelInDBValidationIntegrationTest {
    @MockBean ClassifierValueRepository classifierValueRepository;
    @MockBean ErrorValidationFactory errorsHandler;

    @Autowired
    MedicalRiskLimitLevelInDBValidation validation;

    @Test
    void shouldReturnErrorWhenMedicalRiskLimitLevelNotExistInDb() {

        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "LEVEL_20000"))
                .thenReturn(Optional.empty());


        ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO("ERROR_CODE_14", "Medical risk limit level not found");
        when(errorsHandler.processing("ERROR_CODE_14")).thenReturn(validationErrorDTO);


        PersonDTO person = PersonDTOBuilder
                .createPerson()
                .withPersonFirstName("Никита")
                .withPersonLastName("Морозов")
                .withPersonBirthDate(createDate("25.11.2002"))
                .withRisks(null)
                .withMedicalRiskLimitLevel("LEVEL_20000")
                .build();

        Optional<ValidationErrorDTO> validationErrorOpt = validation.validationOptional(person);

        assertTrue(validationErrorOpt.isPresent());
        assertSame(validationErrorDTO, validationErrorOpt.get());
        assertEquals("ERROR_CODE_14", validationErrorOpt.get().errorCode());
        assertEquals("Medical risk limit level not found", validationErrorOpt.get().description());
    }

    @Test
    void shouldReturnErrorWhenMedicalRiskLimitLevelIsBlank() {
        PersonDTO person = PersonDTOBuilder
                .createPerson()
                .withPersonFirstName("Никита")
                .withPersonLastName("Морозов")
                .withPersonBirthDate(createDate("25.11.2002"))
                .withRisks(null)
                .withMedicalRiskLimitLevel("   ")
                .build();

        ValidationErrorDTO validationErrorDTO = mock(ValidationErrorDTO.class);
        when(errorsHandler.processing("ERROR_CODE_14")).thenReturn(validationErrorDTO);
        Optional<ValidationErrorDTO> validationErrorOpt = validation.validationOptional(person);
        assertTrue(validationErrorOpt.isPresent());
    }

    @Test
    void shouldNotReturnErrorWhenMedicalRiskLimitLevelExistInDb() {
        PersonDTO person = PersonDTOBuilder
                .createPerson()
                .withPersonFirstName("Никита")
                .withPersonLastName("Морозов")
                .withPersonBirthDate(createDate("25.11.2002"))
                .withRisks(null)
                .withMedicalRiskLimitLevel("LEVEL_15000")
                .build();
        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "LEVEL_15000"))
                .thenReturn(Optional.empty());
        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", person.getMedicalRiskLimitLevel())).thenReturn(Optional.of(new ClassifierValue()));
        Optional<ValidationErrorDTO> validationErrorOpt = validation.validationOptional(person);
        assertTrue(validationErrorOpt.isEmpty());
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


