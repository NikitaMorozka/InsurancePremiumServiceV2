package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.medical.MedicalRiskLimitLevel;
import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class MedicalRiskLimitLevelCalculating {

    @Value("${medical.risk.limit.level.enabled}")
    private Boolean medicalRiskLimitLevelEnabled;

    private final MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    BigDecimal findMedicalRiskLimitLevel(PersonDTO request) {
        return medicalRiskLimitLevelEnabled
                ? getCoefficient(request)
                : getDefaultValue();
    }

    private BigDecimal getCoefficient(PersonDTO request) {
        return medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc(request.getMedicalRiskLimitLevel())
                .map(MedicalRiskLimitLevel::getCoefficient)
                .orElseThrow(() ->
                        new RuntimeException("Country not found = " + request.getMedicalRiskLimitLevel()));
    }

    private static BigDecimal getDefaultValue() {
        return BigDecimal.ONE;
    }


}
