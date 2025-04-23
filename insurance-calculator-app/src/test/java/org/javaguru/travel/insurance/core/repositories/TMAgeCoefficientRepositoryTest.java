package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.medical.TMAgeCoefficient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TMAgeCoefficientRepositoryTest {
    @Autowired
    TMAgeCoefficientRepository TMAgeCoefficientRepository;

    @Test
    @DisplayName("Test: Classifier table is present")
    void injectedRepositoryAreNotNull() {
        assertNotNull(TMAgeCoefficientRepository);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void shouldFindRiskTypes(Integer classifierTitle, BigDecimal ic) {
        Optional<TMAgeCoefficient> valueOpt = TMAgeCoefficientRepository.findByAgeCoefficient(classifierTitle);
        assertTrue(valueOpt.isPresent());
        assertEquals(ic.setScale(2, RoundingMode.HALF_UP), valueOpt.get().getCoefficient());
    }

    @Test
    void shouldNotFind_RiskType_FAKE() {
        Optional<TMAgeCoefficient> valueOpt = TMAgeCoefficientRepository.findByAgeCoefficient(151);
        assertTrue(valueOpt.isEmpty());
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(4, new BigDecimal("1.1")),
                Arguments.of(8, new BigDecimal ("0.7")),
                Arguments.of(15, new BigDecimal ("1.0")),
                Arguments.of(25, new BigDecimal ("1.1")),
                Arguments.of(45, new BigDecimal ("1.2")),
                Arguments.of(70, new BigDecimal("1.5"))
        );
    }

}
