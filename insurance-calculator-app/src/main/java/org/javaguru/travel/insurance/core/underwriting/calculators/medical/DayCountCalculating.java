package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DayCountCalculating {

    private final DateTimeUtil dateTimeUtil;

    BigDecimal calculateDayCount(AgreementDTO request) {
        return new BigDecimal(dateTimeUtil.calculateDaysDifference(request.getAgreementDateFrom(), request.getAgreementDateTo()));
    }
}
