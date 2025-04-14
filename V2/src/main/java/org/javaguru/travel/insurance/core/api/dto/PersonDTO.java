package org.javaguru.travel.insurance.core.api.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {

    private String personFirstName;

    private String personLastName;

    private String personCode;

    private LocalDate personBirthDate;

    private String medicalRiskLimitLevel;

    private BigDecimal travelCost;

    private List<RiskDTO> risks;

}




