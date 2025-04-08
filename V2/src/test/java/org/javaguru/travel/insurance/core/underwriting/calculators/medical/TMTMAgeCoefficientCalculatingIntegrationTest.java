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
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TMTMAgeCoefficientCalculatingIntegrationTest {

    @Autowired private TMAgeCoefficientCalculating TMAgeCoefficientCalculating;

    @Test
    void shouldReturnCorrectAgeCoefficient() {
        PersonDTO person = PersonDTOBuilder
                .createPerson()
                .withPersonBirthDate(createDate("25.11.2002"))
                .build();
        BigDecimal result = TMAgeCoefficientCalculating.findAgeCoefficient(person);
        assertEquals(new BigDecimal("1.10"), result);
    }

    @Test
    void shouldThrowExceptionIfAgeNotFound() {
        PersonDTO person = PersonDTOBuilder
                .createPerson()
                .withPersonBirthDate(createDate("25.11.1850"))
                .build();

        assertThrows(RuntimeException.class, () ->
                TMAgeCoefficientCalculating.findAgeCoefficient(person));
    }

    @Test
    void shouldReturnDefaultAgeCoefficient() {
        PersonDTO person = PersonDTOBuilder
                .createPerson()
                .withPersonBirthDate(createDate("25.11.2002"))
                .build();

        ReflectionTestUtils.setField(TMAgeCoefficientCalculating, "medicalRiskAgeCoefficientEnabled", false);

        BigDecimal result = TMAgeCoefficientCalculating.findAgeCoefficient(person);
        assertEquals(new BigDecimal("1"), result);
    }


    private LocalDate createDate(String dateStr) {
        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            date =  LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Некорректный формат даты: " + dateStr, e);
        }
        return date;
    }
}

