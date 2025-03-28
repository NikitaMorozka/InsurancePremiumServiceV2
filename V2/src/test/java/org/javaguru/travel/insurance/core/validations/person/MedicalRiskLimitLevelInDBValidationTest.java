package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRiskLimitLevelInDBValidationTest {
    @Mock PersonDTO request;
    @Mock ClassifierValueRepository classifierValueRepository;
    @Mock ErrorValidationFactory errorsHandler;

    @InjectMocks
    MedicalRiskLimitLevelInDBValidation validation;

    @Test
    void shouldReturnErrorWhenMedicalRiskLimitLevelNotExistInDb() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("TEST_IC");
        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "TEST_IC"))
                .thenReturn(Optional.empty());
        ValidationErrorDTO validationErrorDTO = mock(ValidationErrorDTO.class);
        when(errorsHandler.processing("ERROR_CODE_14")).thenReturn(validationErrorDTO);
        Optional<ValidationErrorDTO> validationErrorOpt = validation.validationOptional(request);
        assertTrue(validationErrorOpt.isPresent());
        assertSame(validationErrorDTO, validationErrorOpt.get());
    }

    @Test
    void shouldReturnErrorWhenMedicalRiskLimitLevelIsBlank() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("   ");
        ValidationErrorDTO validationErrorDTO = mock(ValidationErrorDTO.class);
        when(errorsHandler.processing("ERROR_CODE_14")).thenReturn(validationErrorDTO);
        Optional<ValidationErrorDTO> validationErrorOpt = validation.validationOptional(request);
        assertTrue(validationErrorOpt.isPresent());
    }

    @Test
    void shouldNotReturnErrorWhenMedicalRiskLimitLevelExistInDb() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_15000");
        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "LEVEL_15000"))
                .thenReturn(Optional.empty());
        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", request.getMedicalRiskLimitLevel())).thenReturn(Optional.of(new ClassifierValue()));
        Optional<ValidationErrorDTO> validationErrorOpt = validation.validationOptional(request);
        assertTrue(validationErrorOpt.isEmpty());
    }
}


