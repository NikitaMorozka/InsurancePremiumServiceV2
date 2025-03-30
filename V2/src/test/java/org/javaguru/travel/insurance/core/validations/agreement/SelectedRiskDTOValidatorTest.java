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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SelectedRiskDTOValidatorTest {

    @Mock private ClassifierValueRepository classifierValueRepository;
    @Mock private ErrorValidationFactory errorsHandler;

    @InjectMocks
    SelectedRisksValidator selectedRisksValidator;

    @Test
    void shouldNotValidateWhenSelectedRisksIsNull() {
        AgreementDTO request = mock(AgreementDTO.class);

        when(request.getSelectedRisks()).thenReturn(null);

        assertTrue(selectedRisksValidator.validationList(request).isEmpty());

        verifyNoInteractions(classifierValueRepository, errorsHandler);
    }

    @Test
    void shouldValidateWithoutErrors() {
        AgreementDTO request = mock(AgreementDTO.class);

        when(request.getSelectedRisks()).thenReturn(List.of("RISK_IC_1", "RISK_IC_2"));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "RISK_IC_1"))
                .thenReturn(Optional.of(mock(ClassifierValue.class)));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "RISK_IC_2"))
                .thenReturn(Optional.of(mock(ClassifierValue.class)));

        assertTrue(selectedRisksValidator.validationList(request).isEmpty());
    }

    @Test
    void shouldValidateWithErrors() {
        AgreementDTO request = mock(AgreementDTO.class);

        when(request.getSelectedRisks()).thenReturn(List.of("RISK_IC_1", "RISK_IC_2"));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "RISK_IC_1"))
                .thenReturn(Optional.empty());
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "RISK_IC_2"))
                .thenReturn(Optional.empty());

        ValidationErrorDTO error = mock(ValidationErrorDTO.class);
        when(errorsHandler.processing(eq("ERROR_CODE_8"), anyList())).thenReturn(error);

        assertEquals(2, selectedRisksValidator.validationList(request).size());
    }
}