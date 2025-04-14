package org.javaguru.travel.insurance.core.api.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgreementDTO {

    private LocalDate agreementDateFrom;

    private LocalDate agreementDateTo;

    private UUID uuid;

    private String country;

    private List<String> selectedRisks;

     private List<PersonDTO> persons;

    private BigDecimal agreementPremium;

}
