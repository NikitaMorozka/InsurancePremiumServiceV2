package org.javaguru.blacklist.rest.delete;

import lombok.RequiredArgsConstructor;
import org.javaguru.blacklist.core.api.command.BlackListedPersonDeleteCoreCommand;
import org.javaguru.blacklist.core.api.command.BlackListedPersonDeleteCoreResult;
import org.javaguru.blacklist.core.services.delete.BlackListedPersonDeleteCoreService;
import org.javaguru.blacklist.dto.delete.BlackListedPersonDeleteRequest;
import org.javaguru.blacklist.dto.delete.BlackListedPersonDeleteResponse;
import org.javaguru.blacklist.dto.getAll.PersonResponse;
import org.javaguru.blacklist.rest.all.DtoAllConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blacklist/person/delete")
public class BlackListedPersonController {

    private final BlackListedPersonDeleteCoreService deleteService;
    private final DtoAllConverter dtoAllConverter;

    @PostMapping(path = "/",
            produces = "application/json")
    public BlackListedPersonDeleteResponse deleteBlackListedPerson(@RequestBody BlackListedPersonDeleteRequest request) {
        BlackListedPersonDeleteCoreCommand command = new BlackListedPersonDeleteCoreCommand();
        command.setId(request.getId());
        BlackListedPersonDeleteCoreResult result = deleteService.delete(command);
        List<PersonResponse> personResponses = dtoAllConverter.build(result);
        return new BlackListedPersonDeleteResponse(personResponses);

    }
}