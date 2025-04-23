package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.medical.CountryDefaultDayRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CountryDefaultDayRateRepositoryTest {

    @Autowired
    private CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Test
    void injectedRepositoryAreNotNull() {
        assertNotNull(countryDefaultDayRateRepository);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void searchCountryDefaultDayRate(String countryIc, BigDecimal dayRate) {
        Optional<CountryDefaultDayRate> valueOpt = countryDefaultDayRateRepository.findDefaultDayRateByCountryIc(countryIc);
        assertTrue(valueOpt.isPresent());
        assertEquals(valueOpt.get().getCountryIc(), countryIc);
        assertEquals(valueOpt.get().getDefaultDayRate(), dayRate);
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of("LATVIA", new BigDecimal("1.00")),
                Arguments.of("SPAIN", new BigDecimal("2.50")),
                Arguments.of("JAPAN", new BigDecimal("3.50"))
        );
    }

    @Test
    void shouldNotFindForUnknownCountry() {
        Optional<CountryDefaultDayRate> valueOpt = countryDefaultDayRateRepository.findDefaultDayRateByCountryIc("FAKE_COUNTRY");
        assertTrue(valueOpt.isEmpty());
    }


}
