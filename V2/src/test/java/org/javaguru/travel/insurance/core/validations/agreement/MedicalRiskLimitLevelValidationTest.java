package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRiskLimitLevelValidationTest {
    @Mock ClassifierValueRepository classifierValueRepository;
    @Mock ErrorValidationFactory errorValidationFactory;
    @Mock AgreementDTO request;

    @InjectMocks MedicalRiskLimitLevelValidation medicalRiskLimitLevelValidation;

    @Test
    void shouldNotReturnErrorWhenMedicalRiskLimitLevelNotEnabled() {
        ReflectionTestUtils.setField(medicalRiskLimitLevelValidation, "medicalRiskLimitLevelEnabled", false);

        Optional<ValidationErrorDTO> validationErrorOpt = medicalRiskLimitLevelValidation.validationOptional(request);

        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorValidationFactory);
    }
    @Test
    void shouldNotReturnErrorWhenNotContainTravelMedicalRisk() {
        ReflectionTestUtils.setField(medicalRiskLimitLevelValidation, "medicalRiskLimitLevelEnabled", true);

        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_EVACUATION"));

        Optional<ValidationErrorDTO> validationErrorOpt = medicalRiskLimitLevelValidation.validationOptional(request);
        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorValidationFactory);
    }
    @Test
    void shouldNotReturnErrorWhenMedicalRiskLimitLevelIsNull() {
        ReflectionTestUtils.setField(medicalRiskLimitLevelValidation, "medicalRiskLimitLevelEnabled", true);
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getMedicalRiskLimitLevel()).thenReturn(null);

        Optional<ValidationErrorDTO> validationErrorOpt = medicalRiskLimitLevelValidation.validationOptional(request);

        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorValidationFactory);
    }
    @Test
     void shouldNotReturnErrorWhenMedicalRiskLimitLevelExistInDb() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_10000");
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        ReflectionTestUtils.setField(medicalRiskLimitLevelValidation, "medicalRiskLimitLevelEnabled", true);
        ClassifierValue classifierValue = mock(ClassifierValue.class);
        when(classifierValueRepository.
                findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "LEVEL_10000"))
                .thenReturn(Optional.of(classifierValue));

        Optional<ValidationErrorDTO> validationErrorOpt = medicalRiskLimitLevelValidation.validationOptional(request);

        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(errorValidationFactory);
    }

    @Test
    void shouldReturnError() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_10000");
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        ReflectionTestUtils.setField(medicalRiskLimitLevelValidation, "medicalRiskLimitLevelEnabled", true);
        when(classifierValueRepository.
                findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "LEVEL_10000"))
                .thenReturn(Optional.empty());
        ValidationErrorDTO validationErrorDTO = mock(ValidationErrorDTO.class);

        when(errorValidationFactory.processing("ERROR_CODE_13")).thenReturn(validationErrorDTO);

        Optional<ValidationErrorDTO> validationErrorOpt = medicalRiskLimitLevelValidation.validationOptional(request);

        assertTrue(validationErrorOpt.isPresent());
        assertSame(validationErrorDTO, validationErrorOpt.get());
    }

}

