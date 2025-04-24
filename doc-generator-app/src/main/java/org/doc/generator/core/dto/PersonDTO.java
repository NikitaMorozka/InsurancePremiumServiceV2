package org.doc.generator.core.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PersonDTO {

    private String personFirstName;

    private String personLastName;

    private String personCode;

    @JsonDeserialize(using = LocalDateArrayDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)  // Можно добавить сериализатор для LocalDate
    private LocalDate personBirthDate;

    private String medicalRiskLimitLevel;

    private BigDecimal travelCost;

    private List<RiskDTO> risks;

}
