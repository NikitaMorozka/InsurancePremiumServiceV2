package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CountryInDataBaseValidatorTest {

    @Mock CountryDefaultDayRateRepository countryDefaultDayRateRepository;
    @Mock
    ErrorValidationFactory errorsHandler;
    @Mock
    AgreementDTO request;

    @InjectMocks
    CountryInDataBaseValidator countryInDataBaseValidator;

    @Test
    @DisplayName("Тест: Страна нет в БД")
    void shouldReturnErrorWhenCountryNotInRepository() {
        when(request.getCountry()).thenReturn("NoNameCountry");
        when(countryDefaultDayRateRepository.findDefaultDayRateByCountryIc("NoNameCountry"))
                .thenReturn(Optional.empty());
        when(errorsHandler.processing("ERROR_CODE_9"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_9", "Country not in DataBase"));

        Optional<ValidationErrorDTO> result = countryInDataBaseValidator.validationOptional(request);

        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_9", result.get().errorCode());
        assertEquals("Country not in DataBase", result.get().description());
    }

    @Test
    @DisplayName("Тест: Страна есть в БД")
    void shouldNotReturnErrorWhenCountryFoundInRepository() {
        when(request.getCountry()).thenReturn("JAPAN");
        when(countryDefaultDayRateRepository.findDefaultDayRateByCountryIc("JAPAN")).thenReturn(Optional.of(new CountryDefaultDayRate()));

        Optional<ValidationErrorDTO> result = countryInDataBaseValidator.validationOptional(request);

        assertTrue(result.isEmpty());
    }

}
