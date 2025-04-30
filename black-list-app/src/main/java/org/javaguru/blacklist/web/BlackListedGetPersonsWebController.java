package org.javaguru.blacklist.web;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.blacklist.core.api.command.BlackListedPersonsListCoreCommand;
import org.javaguru.blacklist.core.api.command.BlackListedPersonsListCoreResult;
import org.javaguru.blacklist.core.services.all.BlackListedGetPersonsService;
import org.javaguru.blacklist.dto.check.BlackListedPersonCheckRequest;
import org.javaguru.blacklist.dto.getAll.BlackListedGetPersonsResponse;
import org.javaguru.blacklist.dto.getAll.PersonResponse;
import org.javaguru.blacklist.rest.all.DtoAllConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


///Переделать !!!!!!
// добавить логгер

@Controller
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@RequestMapping("/blacklist/persons/get/web")
public class BlackListedGetPersonsWebController {
//   http://localhost:8090/blacklist/persons/get/web

    private final BlackListedGetPersonsService blackListedGetPersonsService;
    private final DtoAllConverter dtoAllConverter;

    @GetMapping("")
        public String showForm(ModelMap modelMap) {
        modelMap.addAttribute("response", new BlackListedGetPersonsResponse());
        return "black-listed-get-all";
    }

    @PostMapping("")
    public String processForm(@ModelAttribute(value = "request") BlackListedPersonCheckRequest request,
                              ModelMap modelMap) {
        BlackListedPersonsListCoreResult result = blackListedGetPersonsService.getAllBlackListedPersons(new BlackListedPersonsListCoreCommand());
        List<PersonResponse> response = dtoAllConverter.build(result);
        modelMap.addAttribute("response", new BlackListedGetPersonsResponse(response));
        return "black-listed-get-all";
    }

}
