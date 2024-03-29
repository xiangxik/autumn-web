package com.autumn.endpoint;

import com.autumn.modules.core.service.CorpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@Controller
public class LoginEndpoint {

    @Autowired
    private CorpService corpService;

    @GetMapping(value = "/login", params = "!error")
    public String loginPage(Model model) {
        model.addAttribute("corps", corpService.findAll());
        return "/login";
    }

    @GetMapping(value = "/login", params = "error")
    public String loginErrorPage(Model model, HttpSession session) {
        String errorMsg = "Invalid credentials";
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            errorMsg = ex != null ? ex.getMessage() : "Invalid credentials";
        }
        model.addAttribute("errorMsg", HtmlUtils.htmlEscape(errorMsg));
        return loginPage(model);
    }

}
