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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)

class DayCountCalculatingIntegrationTest {

    @Autowired
    DayCountCalculating dayCountCalculating;

    @Test
    void shouldReturnCorrectDayCount(){
        AgreementDTO agreement = AgreementDTOBuilder.createAgreement()
                .withDateFrom(createDate("01.01.2021"))
                .withDateTo(createDate("01.01.2022"))
                .withPersons(List.of())
                .build();

        BigDecimal result = dayCountCalculating.calculateDayCount(agreement);
        assertEquals(new BigDecimal(365L), result);
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
