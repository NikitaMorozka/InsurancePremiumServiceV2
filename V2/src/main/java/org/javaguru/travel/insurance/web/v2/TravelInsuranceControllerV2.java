package org.javaguru.travel.insurance.web.v2;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.v2.DtoV2Converter;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumRequestV2;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumResponseV2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)

public class TravelInsuranceControllerV2 {

    private final DtoV2Converter dtoV2Converter;

    @GetMapping("/insurance/travel/web/v2")
    public String showForm(ModelMap modelMap) {
        modelMap.addAttribute("request", new TravelCalculatePremiumRequestV2());
        modelMap.addAttribute("response", new TravelCalculatePremiumResponseV2());
        return "travel-calculate-premiumV2";
    }

    @PostMapping("/insurance/travel/web/v2")
    public String processForm(@ModelAttribute(value = "request") TravelCalculatePremiumRequestV2 request,
                              ModelMap modelMap) {
        TravelCalculatePremiumResponseV2 response = dtoV2Converter.processRequest(request);
        modelMap.addAttribute("response", response);
        return "travel-calculate-premiumV2";
    }
}
