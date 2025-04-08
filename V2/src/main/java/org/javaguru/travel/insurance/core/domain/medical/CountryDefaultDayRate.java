package org.javaguru.travel.insurance.core.domain.medical;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "country_default_day_rate")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CountryDefaultDayRate {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // генерируем числа по порядку
    private Long id;

    @Column(name = "country_ic", nullable = false)
    private String countryIc;

    @Column(name = "default_day_rate",precision = 10, scale = 2, nullable = false)
    private BigDecimal defaultDayRate;

}
