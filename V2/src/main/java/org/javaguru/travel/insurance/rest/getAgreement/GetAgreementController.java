package org.javaguru.travel.insurance.rest.getAgreement;


import com.google.common.base.Stopwatch;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import org.javaguru.travel.insurance.core.services.TravelGetAgreementService;
import org.javaguru.travel.insurance.dto.GetAgreementDTO.GetAgreementDtoConverter;
import org.javaguru.travel.insurance.dto.GetAgreementDTO.GetAgreementResponse;
import org.javaguru.travel.insurance.rest.logger.EventLogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/insurance/travel/api/internal/agreement")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetAgreementController {

    private final TravelGetAgreementService travelGetAgreementService;
    private final GetAgreementDtoConverter getAgreementDtoConverter;
    private final List<EventLogger> loggers;
    private Stopwatch stopwatch;

    @GetMapping(path = "/{uuid}",
            produces = "application/json")
    public GetAgreementResponse showUuid(@PathVariable("uuid") UUID uuid) {
        return buildResponse(uuid);
    }
    private GetAgreementResponse buildResponse(UUID uuid) {
        stopwatch = Stopwatch.createStarted();
        return processRequest(uuid);
    }

    private GetAgreementResponse processRequest(UUID uuid){
        TravelGetAgreementCoreCommand travelGetAgreementCoreCommand = getAgreementDtoConverter.processRequest(uuid);
        logObject(travelGetAgreementCoreCommand);

        TravelGetAgreementCoreResult travelGetAgreementCoreResult =  travelGetAgreementService.getAgreement(travelGetAgreementCoreCommand);
        logObject(travelGetAgreementCoreResult);
        logObject(stopwatch);
        return getAgreementDtoConverter.buildResponse(travelGetAgreementCoreResult);
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