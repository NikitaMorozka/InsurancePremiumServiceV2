package org.javaguru.travel.insurance.core.api.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskDTO {
    private String riskIc;

    private BigDecimal premium;

}
