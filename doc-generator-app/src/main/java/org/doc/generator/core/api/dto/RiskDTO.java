package org.doc.generator.core.api.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class RiskDTO {

    private String riskIc;

    private BigDecimal premium;

}
