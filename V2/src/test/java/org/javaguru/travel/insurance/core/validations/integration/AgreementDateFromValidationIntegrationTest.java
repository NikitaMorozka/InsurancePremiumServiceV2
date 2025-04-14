package org.javaguru.travel.insurance.core.validations.integration;

import org.javaguru.travel.insurance.core.api.dto.*;
import org.javaguru.travel.insurance.core.validations.TravelAgreementValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AgreementDateFromValidationIntegrationTest {

    @Autowired
    private TravelAgreementValidator validator;

    @Test
    void shouldReturnErrorWhenDateFromIsNull() {
        PersonDTO person = PersonDTO.builder()
                .personFirstName("Никита")
                .personLastName("Морозов")
                .personCode("12341")
                .personBirthDate(createDate("25.11.2002"))
                .medicalRiskLimitLevel("LEVEL_20000")
                .build();

        AgreementDTO agreement = AgreementDTO.builder()
                .agreementDateFrom(null)
                .agreementDateTo(createDate("01.01.2030"))
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .persons(List.of(person))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreement);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_3", errors.getFirst().errorCode());
        assertEquals("DateFrom must not be null!", errors.getFirst().description());
    }

    @Test
     void shouldReturnErrorWhenDateFromIsInThePast() {
        PersonDTO person = PersonDTO.builder()
                .personFirstName("Никита")
                .personLastName("Морозов")
                .personCode("12341")
                .personBirthDate(createDate("25.11.2002"))
                .medicalRiskLimitLevel("LEVEL_20000")
                .build();

        AgreementDTO agreement = AgreementDTO.builder()
                .agreementDateFrom(createDate("01.01.2020"))
                .agreementDateTo(createDate("01.01.2030"))
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .persons(List.of(person))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreement);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_6", errors.getFirst().errorCode());
        assertEquals("Both DateFrom and DateTo must be in the future!", errors.getFirst().description());
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
