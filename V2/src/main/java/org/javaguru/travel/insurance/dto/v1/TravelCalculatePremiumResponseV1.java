package org.javaguru.travel.insurance.dto.v1;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.travel.insurance.dto.CoreResponse;
import org.javaguru.travel.insurance.dto.Risks;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.javaguru.travel.insurance.dto.util.BigDecimalSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelCalculatePremiumResponseV1 extends CoreResponse {
    private String personFirstName;
    private String personLastName;
    private String personCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") private LocalDate dateOfBirth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") private LocalDate agreementDateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") private LocalDate agreementDateTo;
    private String country;
    private String medicalRiskLimitLevel;
    @JsonSerialize(using = BigDecimalSerializer.class) private BigDecimal agreementPremium;
    private List<Risks> risks;

    public TravelCalculatePremiumResponseV1(List<ValidationError> errors) {
        super(errors);
    }

}
