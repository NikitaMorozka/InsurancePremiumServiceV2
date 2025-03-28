package org.javaguru.travel.insurance.core.validations.integration;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTOBuilder;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.TravelAgreementValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AgreementDateFromValidationIntegrationTest {

    @Autowired
    private TravelAgreementValidator validator;

    @Test
    void shouldReturnErrorWhenDateFromIsNull() {
        PersonDTO person = new PersonDTO();
        person.setPersonFirstName("Vasja");
        person.setPersonLastName("Pupkin");
        person.setPersonBirthDate(createDate("01.01.2000"));
        person.setMedicalRiskLimitLevel("LEVEL_10000");

        AgreementDTO agreement = AgreementDTOBuilder.createAgreement()
                .withDateFrom(null)
                .withDateTo(createDate("01.01.2030"))
                .withCountry("SPAIN")
                .withSelectedRisk("TRAVEL_MEDICAL")
                .withPersons(List.of(person))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreement);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_2", errors.getFirst().errorCode());
        assertEquals("Field agreementDateFrom must not be empty!", errors.getFirst().description());
    }

    @Test
     void shouldReturnErrorWhenDateFromIsInThePast() {
        PersonDTO person = new PersonDTO();
        person.setPersonFirstName("Vasja");
        person.setPersonLastName("Pupkin");
        person.setPersonBirthDate(createDate("01.01.2000"));
        person.setMedicalRiskLimitLevel("LEVEL_10000");

        AgreementDTO agreement = AgreementDTOBuilder.createAgreement()
                .withDateFrom(createDate("01.01.2020"))
                .withDateTo(createDate("01.01.2030"))
                .withCountry("SPAIN")
                .withSelectedRisk("TRAVEL_MEDICAL")
                .withPersons(List.of(person))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreement);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_1", errors.getFirst().errorCode());
        assertEquals("Field agreementDateFrom must be in the future!", errors.getFirst().description());
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
