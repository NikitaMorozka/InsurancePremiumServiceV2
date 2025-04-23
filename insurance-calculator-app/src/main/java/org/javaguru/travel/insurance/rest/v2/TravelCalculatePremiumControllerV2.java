package org.javaguru.travel.insurance.rest.v2;


import com.google.common.base.Stopwatch;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.v2.DtoV2Converter;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumRequestV2;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumResponseV2;
import org.javaguru.travel.insurance.rest.logger.EventLogger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/insurance/travel/api/v2")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelCalculatePremiumControllerV2 {

    private final List<EventLogger> loggers;
    private final DtoV2Converter dtoV2Converter;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public TravelCalculatePremiumResponseV2 calculatePremium(@RequestBody TravelCalculatePremiumRequestV2 request) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        logObject(request);
        TravelCalculatePremiumResponseV2 response = dtoV2Converter.processRequest(request);
        logObject(response);
        logObject(stopwatch);
        return response;
    }


    private void logObject(Object obj) {
        loggers.forEach(logger -> {
            try {
                logger.getLog(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}