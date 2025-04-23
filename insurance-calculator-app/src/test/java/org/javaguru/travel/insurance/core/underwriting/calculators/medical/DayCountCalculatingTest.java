package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class DayCountCalculatingTest {

    @Mock
    AgreementDTO request;
    @Mock DateTimeUtil dateTimeUtil;

    @InjectMocks
    DayCountCalculating dayCountCalculating;
    @Test
    void shouldReturnCorrectDayCount(){
        when(dateTimeUtil.calculateDaysDifference(request.getAgreementDateFrom(), request.getAgreementDateTo())).thenReturn(10L);
        BigDecimal result = dayCountCalculating.calculateDayCount(request);
        assertEquals(new BigDecimal(10L), result);
    }

}
