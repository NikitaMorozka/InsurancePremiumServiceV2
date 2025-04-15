package org.javaguru.travel.insurance.core.api.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.*;
import org.javaguru.travel.insurance.core.api.dto.adapter.LocalDateAdapter;
import org.javaguru.travel.insurance.core.api.dto.adapter.UuidAdapter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement

@XmlAccessorType(XmlAccessType.FIELD)

public class AgreementDTO {

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate agreementDateFrom;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate agreementDateTo;

    @XmlJavaTypeAdapter(UuidAdapter.class)
    private UUID uuid;

    private String country;

    @XmlElementWrapper(name = "selectedRisks")
    @XmlElement(name = "risk")
    private List<String> selectedRisks;

    @XmlElementWrapper(name = "persons")
    @XmlElement(name = "person")
    private List<PersonDTO> persons;

    private BigDecimal agreementPremium;

}
