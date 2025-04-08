package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.medical.TMAgeCoefficient;
import org.javaguru.travel.insurance.core.repositories.TMAgeCoefficientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TMAgeCoefficientCalculating {

    @Value("${medical.risk.age.coefficient.enabled}")
    private boolean medicalRiskAgeCoefficientEnabled;

    private final TMAgeCoefficientRepository ageCoefficient;

    public BigDecimal findAgeCoefficient(PersonDTO request) {
        return medicalRiskAgeCoefficientEnabled
                ? getAgeCoefficient(request)
                : getDefaultValue();
    }

    private BigDecimal getAgeCoefficient(PersonDTO request) {
        int age = (int) ChronoUnit.YEARS.between(request.getPersonBirthDate(), LocalDate.now());
        return ageCoefficient.findByAgeCoefficient(age)
                .map(TMAgeCoefficient::getCoefficient)
                .orElseThrow(() ->
                        new RuntimeException("Coefficient not found = " + age));
    }


    private static BigDecimal getDefaultValue() {
        return BigDecimal.ONE;
    }

}

