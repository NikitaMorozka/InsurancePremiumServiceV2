package org.javaguru.travel.insurance.rest.v1;


import com.google.common.base.Stopwatch;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.services.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.v1.DtoV1Converter;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.javaguru.travel.insurance.rest.v1.logger.EventLogger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/insurance/travel/api/v1")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelCalculatePremiumController {

    private final TravelCalculatePremiumService calculatePremiumService;
    private final List<EventLogger> loggers;
    private final DtoV1Converter  dtoV1Converter;
    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")

    public TravelCalculatePremiumResponseV1 calculatePremium(@RequestBody TravelCalculatePremiumRequestV1 request) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        logObject(request);
        TravelCalculatePremiumResponseV1 response = processRequest(request);
        logObject(response);
        logObject(stopwatch);
        return response;
    }

    private TravelCalculatePremiumResponseV1 processRequest(TravelCalculatePremiumRequestV1 request) {
        TravelCalculatePremiumCoreCommand coreCommand = dtoV1Converter.buildCoreCommand(request);
        TravelCalculatePremiumCoreResult coreResult = calculatePremiumService.calculatePremium(coreCommand);
        return dtoV1Converter.buildResponse(coreResult);
    }
//
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