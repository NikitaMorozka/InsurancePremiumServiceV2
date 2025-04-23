package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRiskLimitLevelInDBValidationTest {
    @Mock
    PersonDTO request;
    @Mock
    AgreementDTO agreementDTO;
    @Mock
    ClassifierValueRepository classifierValueRepository;
    @Mock
    ErrorValidationFactory errorsHandler;

    @InjectMocks
    MedicalRiskLimitLevelInDBValidation validation;

    @Test
    void shouldReturnErrorWhenMedicalRiskLimitLevelIsBlank() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("   ");
        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "   "))
                .thenReturn(Optional.empty());
        when(agreementDTO.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(errorsHandler.processing("ERROR_CODE_14"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_14", "Field personFirstName is empty!"));
        Optional<ValidationErrorDTO> validationErrorOpt = validation.validationOptional(agreementDTO, request);
        assertTrue(validationErrorOpt.isPresent());
    }

    @Test
    void shouldNotReturnErrorWhenMedicalRiskLimitLevelExistInDb() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_15000");
        Optional<ValidationErrorDTO> validationErrorOpt = validation.validationOptional(agreementDTO, request);
        assertTrue(validationErrorOpt.isEmpty());
    }
}


