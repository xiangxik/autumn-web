package com.autumn.endpoint;

import com.autumn.modules.core.service.CorpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginEndpoint {

    @Autowired
    private CorpService corpService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("corps", corpService.findAll());
        return "/login";
    }

}
