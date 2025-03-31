package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTOBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)

class MedicalRiskLimitLevelCalculatingIntegrationTest {

    @Autowired
    MedicalRiskLimitLevelCalculating medicalRiskLimitLevelCalculating;

    @Test
    void shouldReturnCorrectAgeCoefficient() {
        PersonDTO person = PersonDTOBuilder
                .createPerson()
                .withMedicalRiskLimitLevel("LEVEL_20000")
                .build();

        BigDecimal result = medicalRiskLimitLevelCalculating.findMedicalRiskLimitLevel(person);
        assertEquals(new BigDecimal("1.50"), result);
    }

    @Test
    void shouldThrowExceptionIfCountryNotFound() {
        PersonDTO person = PersonDTOBuilder
                .createPerson()
                .withMedicalRiskLimitLevel("UNKNOWN")
                .build();

        assertThrows(RuntimeException.class, () ->
                medicalRiskLimitLevelCalculating.findMedicalRiskLimitLevel(person));
    }

}