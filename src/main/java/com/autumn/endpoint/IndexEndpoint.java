package com.autumn.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexEndpoint {

    @GetMapping({"", "/"})
    public String indexPage() {
        return "/index";
    }

    @GetMapping({"/home", "/index"})
    public String homePage() {
        return "/home";
    }
}
