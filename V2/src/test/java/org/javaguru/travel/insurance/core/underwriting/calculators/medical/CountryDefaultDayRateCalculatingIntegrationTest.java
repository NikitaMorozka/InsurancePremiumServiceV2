package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTOBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CountryDefaultDayRateCalculatingIntegrationTest {
    @Autowired
    CountryDefaultDayRateCalculating countryDefaultDayRateCalculating;

    @Test
    void shouldReturnCorrectCountryDefaultDayRate() {
        AgreementDTO agreement = AgreementDTOBuilder
                .createAgreement()
                .withCountry("SPAIN")
                .build();

        BigDecimal result = countryDefaultDayRateCalculating.findCountryDefaultDayRate(agreement).setScale(2, RoundingMode.HALF_UP);
        assertEquals(new BigDecimal("2.50"), result);
    }

    @Test
    void shouldThrowExceptionIfCountryNotFound() {
        AgreementDTO agreement =AgreementDTOBuilder
                .createAgreement()
                .withCountry("UNKNOWN")
                .build();

        assertThrows(RuntimeException.class, () ->
                countryDefaultDayRateCalculating.findCountryDefaultDayRate(agreement));
    }
}
