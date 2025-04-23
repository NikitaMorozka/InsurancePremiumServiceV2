package org.javaguru.travel.insurance.core.api.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "risk")
@XmlAccessorType(XmlAccessType.FIELD)
public class RiskDTO {
    private String riskIc;

    private BigDecimal premium;

}
