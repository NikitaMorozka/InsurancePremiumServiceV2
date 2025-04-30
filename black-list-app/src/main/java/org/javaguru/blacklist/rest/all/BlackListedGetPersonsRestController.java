package org.javaguru.blacklist.rest.all;

import lombok.RequiredArgsConstructor;
import org.javaguru.blacklist.core.api.command.BlackListedPersonsListCoreCommand;
import org.javaguru.blacklist.core.api.command.BlackListedPersonsListCoreResult;
import org.javaguru.blacklist.core.services.all.BlackListedGetPersonsService;
import org.javaguru.blacklist.dto.getAll.BlackListedGetPersonsResponse;
import org.javaguru.blacklist.dto.getAll.PersonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


///Переделать !!!!!!
// добавить логгер

@RestController
@RequiredArgsConstructor
@RequestMapping("/blacklist/persons/get")
public class BlackListedGetPersonsRestController {
//localhost:8090/blacklist/persons/get/
    private final BlackListedGetPersonsService blackListedGetPersonsService;
    private final DtoAllConverter dtoAllConverter;

    @GetMapping(path = "/",
            produces = "application/json")
    public BlackListedGetPersonsResponse getAllBlackListedPersons() {
        BlackListedPersonsListCoreResult result = blackListedGetPersonsService.getAllBlackListedPersons(new BlackListedPersonsListCoreCommand());
        List<PersonResponse> personResponses = dtoAllConverter.build(result);
        return new BlackListedGetPersonsResponse(personResponses);
    }
}
