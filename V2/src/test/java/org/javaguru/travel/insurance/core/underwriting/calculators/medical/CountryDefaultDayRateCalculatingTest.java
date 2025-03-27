package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryDefaultDayRateCalculatingTest {

    @Mock CountryDefaultDayRateRepository countryDefaultDayRateRepository;
    @Mock
    AgreementDTO request;

    @InjectMocks
    CountryDefaultDayRateCalculating countryDefaultDayRateCalculating;

    @Test
    void shouldReturnCorrectCountryDefaultDayRate(){
        when(request.getCountry()).thenReturn("SPAIN");
        CountryDefaultDayRate countryDefaultDayRate = new CountryDefaultDayRate(
                1L, "SPAIN", new BigDecimal(2.8)
        );
        when(countryDefaultDayRateRepository.findDefaultDayRateByCountryIc("SPAIN")).thenReturn(Optional.of(countryDefaultDayRate));
        BigDecimal result = countryDefaultDayRateCalculating.findCountryDefaultDayRate(request).setScale(2, RoundingMode.HALF_UP);
        assertEquals(new BigDecimal("2.80"), result);

    }

}
