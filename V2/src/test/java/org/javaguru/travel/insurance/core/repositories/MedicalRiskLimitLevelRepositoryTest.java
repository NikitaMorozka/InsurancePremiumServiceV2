package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.medical.MedicalRiskLimitLevel;
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
class MedicalRiskLimitLevelRepositoryTest {
    @Autowired
    MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    @Test
    void injectedRepositoryAreNotNull() {
        assertNotNull(medicalRiskLimitLevelRepository);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void shouldFindRiskTypes(String level, BigDecimal coefficient) {
        Optional<MedicalRiskLimitLevel> valueOpt = medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc(level);
        assertTrue(valueOpt.isPresent());
        assertEquals(coefficient.setScale(2, RoundingMode.HALF_UP), valueOpt.get().getCoefficient());
    }

    @Test
    void shouldNotFind_RiskType_FAKE() {
        Optional<MedicalRiskLimitLevel> valueOpt = medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc("LEVEL_55555");
        assertTrue(valueOpt.isEmpty());
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of("LEVEL_10000", new BigDecimal("1.0")),
                Arguments.of("LEVEL_15000", new BigDecimal ("1.2")),
                Arguments.of("LEVEL_20000", new BigDecimal ("1.5")),
                Arguments.of("LEVEL_50000", new BigDecimal ("2.0"))
        );
    }


}

