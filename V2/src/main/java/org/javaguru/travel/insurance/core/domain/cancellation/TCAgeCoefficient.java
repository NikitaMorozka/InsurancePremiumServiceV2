package org.javaguru.travel.insurance.core.domain.cancellation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "travel_cancellation_age_coefficient")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TCAgeCoefficient {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // генерируем числа по порядку
    private Long id;

    @Column(name = "age_from", nullable = false)
    private Integer ageFrom;

    @Column(name = "age_to", nullable = false)
    private Integer ageTo;

    @Column(name = "coefficient", precision = 10, scale = 2, nullable = false)
    private BigDecimal coefficient;
}
