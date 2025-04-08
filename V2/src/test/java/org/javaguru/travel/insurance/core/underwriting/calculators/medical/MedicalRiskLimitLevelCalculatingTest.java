package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.medical.MedicalRiskLimitLevel;
import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRiskLimitLevelCalculatingTest {

    @Mock MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;
    @Mock PersonDTO request;

    @InjectMocks
    MedicalRiskLimitLevelCalculating medicalRiskLimitLevelCalculating;

    @Test
    void shouldReturnCorrectAgeCoefficient() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_15000");
        MedicalRiskLimitLevel medicalRiskLimitLevel =
                new MedicalRiskLimitLevel(1L, "LEVEL_15000", new BigDecimal("1.20"));
        ReflectionTestUtils.setField(medicalRiskLimitLevelCalculating, "medicalRiskLimitLevelEnabled", true);
        when(medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc(request.getMedicalRiskLimitLevel())).thenReturn(Optional.of(medicalRiskLimitLevel));
        BigDecimal result = medicalRiskLimitLevelCalculating.findMedicalRiskLimitLevel(request);
        assertEquals(new BigDecimal("1.20"), result);
    }
}