package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.cancellation.TravelCostCoefficient;
import org.javaguru.travel.insurance.core.repositories.TravelCostCoefficientRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelCostCostCoefficientCalculator {
    private final TravelCostCoefficientRepository travelCostCoefficientRepository;

    public BigDecimal getCostCoefficient(PersonDTO personDTO) {
        return travelCostCoefficientRepository.findByCostCoefficient(personDTO.getTravelCost())
                .map(TravelCostCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Travel Cost coefficient not found for travel cost = " + personDTO.getTravelCost()));
    }
}
//Ваша задача:
//разработайте код для задачи 3:
//реализация CountrySafetyRatingCoefficient.
//
//CountrySafetyRatingCoefficient - рейтинг безопасности
//страны в которую едет путешественник (допустимые значения 0 - 10,
//чем значение выше тем безопаснее страна).
//Этот рейтинг может быть задан в базе данных следующим образом:
//LATVIA - 5
//SPAIN - 8
//JAPAN - 9
//CountrySafetyRatingCoefficient =
//стоимость путешествия / рейтинг безопасности страны.