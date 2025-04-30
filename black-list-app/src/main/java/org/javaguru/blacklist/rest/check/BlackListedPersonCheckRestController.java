package org.javaguru.blacklist.rest.check;

import com.google.common.base.Stopwatch;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.blacklist.core.api.command.BlackListedPersonCoreCommand;
import org.javaguru.blacklist.core.api.command.BlackListedPersonCoreResult;
import org.javaguru.blacklist.core.services.check.BlackListedPersonService;
import org.javaguru.blacklist.dto.check.BlackListedPersonCheckRequest;
import org.javaguru.blacklist.dto.check.BlackListedPersonCheckResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@RequestMapping("/blacklist/person/check")
public class BlackListedPersonCheckRestController {

    private final BlackListedPersonRequestLogger requestLogger;
    private final BlackListedPersonResponseLogger responseLogger;
    private final RestRequestExecutionTimeLogger executionTimeLogger;
    private final DtoCheckConverter dtoCheckConverter;
    private final BlackListedPersonService blackListedPersonService;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public BlackListedPersonCheckResponse calculatePremium(@RequestBody BlackListedPersonCheckRequest request) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        BlackListedPersonCheckResponse response = processRequest(request);
        executionTimeLogger.logExecutionTime(stopwatch);
        return response;
    }

    private BlackListedPersonCheckResponse processRequest(BlackListedPersonCheckRequest request) {
        requestLogger.log(request);
        BlackListedPersonCoreCommand coreCommand = dtoCheckConverter.buildCoreCommand(request);
        BlackListedPersonCoreResult coreResult = blackListedPersonService.check(coreCommand);
        BlackListedPersonCheckResponse response = dtoCheckConverter.buildResponse(coreResult);
        responseLogger.log(response);
        return response;
    }

}
