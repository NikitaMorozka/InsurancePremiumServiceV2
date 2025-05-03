package org.javaguru.travel.insurance.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TabController {
//http://localhost:8080/index
    @GetMapping("/index")
    public String showTabsPage() {
        return "index";  // имя шаблона (index.html)
    }
}