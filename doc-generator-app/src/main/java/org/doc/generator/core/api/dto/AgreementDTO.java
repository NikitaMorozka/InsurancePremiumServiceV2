package org.doc.generator.core.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.doc.generator.core.util.LocalDateArrayDeserializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgreementDTO {

    @JsonDeserialize(using = LocalDateArrayDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)  // Можно добавить сериализатор для LocalDate
    private LocalDate agreementDateFrom;
    @JsonDeserialize(using = LocalDateArrayDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)  // Можно добавить сериализатор для LocalDate
    private LocalDate agreementDateTo;
    private UUID uuid;

    private String country;
    private List<String> selectedRisks;
    private List<PersonDTO> persons;

    private BigDecimal agreementPremium;

}
