package org.javaguru.travel.insurance.core.api.dto;

import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.*;
import org.javaguru.travel.insurance.core.api.dto.adapter.LocalDateAdapter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)


public class PersonDTO {

    @Size(max = 200)
    private String personFirstName;

    @Size(max = 200)
    private String personLastName;

    private String personCode;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate personBirthDate;

    private String medicalRiskLimitLevel;

    private BigDecimal travelCost;

    @XmlElementWrapper(name = "risks")
    @XmlElement(name = "risk")
    private List<RiskDTO> risks;

}




