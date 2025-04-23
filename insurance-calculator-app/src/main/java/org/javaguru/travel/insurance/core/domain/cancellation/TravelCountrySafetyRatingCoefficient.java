package org.javaguru.travel.insurance.core.domain.cancellation;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "travel_cancellation_country_safety_rating")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TravelCountrySafetyRatingCoefficient {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_ic", nullable = false)
    private String countryIc;

    @Column(name = "coefficient", precision = 10, scale = 10, nullable = false)
    private BigDecimal coefficient;

}
