package org.javaguru.blacklist.web;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.blacklist.core.api.command.BlackListedPersonCoreCommand;
import org.javaguru.blacklist.core.api.command.BlackListedPersonCoreResult;
import org.javaguru.blacklist.core.services.сheck.BlackListedPersonService;
import org.javaguru.blacklist.dto.check.BlackListedPersonCheckRequest;
import org.javaguru.blacklist.dto.check.BlackListedPersonCheckResponse;
import org.javaguru.blacklist.rest.check.DtoCheckConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@RequestMapping("/blacklist/person/check/web")
public class BlackListedPersonCheckWebController {

    private final DtoCheckConverter dtoCheckConverter;
    //              http://localhost:8090/blacklist/person/check/web
    private final BlackListedPersonService blackListedPersonService;

    @GetMapping("")
    public String showForm(ModelMap modelMap) {
        modelMap.addAttribute("request", new BlackListedPersonCheckRequest());
        modelMap.addAttribute("response", new BlackListedPersonCheckResponse());
        return "black-listed-check";
    }

    @PostMapping("")
    public String processForm(@ModelAttribute(value = "request") BlackListedPersonCheckRequest request,
                              ModelMap modelMap) {
        BlackListedPersonCoreCommand coreCommand = dtoCheckConverter.buildCoreCommand(request);
        BlackListedPersonCoreResult coreResult = blackListedPersonService.check(coreCommand);
        BlackListedPersonCheckResponse response = dtoCheckConverter.buildResponse(coreResult);
        modelMap.addAttribute("response", response);
        return "black-listed-check";
    }
}





