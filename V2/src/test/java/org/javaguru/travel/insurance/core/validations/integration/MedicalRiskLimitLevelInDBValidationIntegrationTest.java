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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
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
        AgreementDTO agreementDTO = new AgreementDTO();

        RiskDTO riskDTO = RiskDTO
                .builder()
                .riskIc("MEDICAL_RISK_LIMIT_LEVEL")
                .premium(new BigDecimal("1.1"))
                .build();

        PersonDTO person = PersonDTO
                .builder()
                .personBirthDate(createDate())
                .risks(List.of(riskDTO))
                .medicalRiskLimitLevel("LEVEL_20000")
                .build();


        when(classifierValueRepository.findByClassifierTitleAndIc(riskDTO.getRiskIc(), person.getMedicalRiskLimitLevel()))
                .thenReturn(Optional.empty());

        ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO("ERROR_CODE_14", "Medical risk limit level not found");
        when(errorsHandler.processing("ERROR_CODE_14")).thenReturn(validationErrorDTO);

        Optional<ValidationErrorDTO> validationErrorOpt = validation.validationOptional(agreementDTO, person);

        assertTrue(validationErrorOpt.isPresent());
        assertSame(validationErrorDTO, validationErrorOpt.get());
        assertEquals("ERROR_CODE_14", validationErrorOpt.get().errorCode());
        assertEquals("Medical risk limit level not found", validationErrorOpt.get().description());
    }

    @Test
    void shouldReturnErrorWhenMedicalRiskLimitLevelIsBlank() {
        AgreementDTO agreementDTO = new AgreementDTO();

        PersonDTO person = PersonDTO
                .builder()
                .personBirthDate(createDate())
                .risks(null)
                .medicalRiskLimitLevel("   ")
                .build();

        ValidationErrorDTO validationErrorDTO = mock(ValidationErrorDTO.class);
        when(errorsHandler.processing("ERROR_CODE_14")).thenReturn(validationErrorDTO);
        Optional<ValidationErrorDTO> validationErrorOpt = validation.validationOptional(agreementDTO, person);
        assertTrue(validationErrorOpt.isPresent());
    }

    @Test
    void shouldNotReturnErrorWhenMedicalRiskLimitLevelExistInDb() {
        AgreementDTO agreementDTO = new AgreementDTO();

        RiskDTO riskDTO = RiskDTO
                .builder()
                .riskIc("MEDICAL_RISK_LIMIT_LEVEL")
                .premium(new BigDecimal("1.1"))
                .build();

        PersonDTO person = PersonDTO.builder()
                .personBirthDate(createDate())
                .risks(List.of(riskDTO))
                .medicalRiskLimitLevel("LEVEL_15000")
                .build();

        when(classifierValueRepository.findByClassifierTitleAndIc(riskDTO.getRiskIc(), person.getMedicalRiskLimitLevel())).thenReturn(Optional.of(new ClassifierValue()));
        Optional<ValidationErrorDTO> validationErrorOpt = validation.validationOptional(agreementDTO, person);
        assertTrue(validationErrorOpt.isEmpty());
    }

    private LocalDate createDate() {
        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            date =  LocalDate.parse("25.11.2002", formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Некорректный формат даты: " + "25.11.2002", e);
        }
        return date;
    }
}


