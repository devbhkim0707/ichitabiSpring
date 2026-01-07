package com.ichiban.ichitabi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/japanMap")
    public String japanMap() {
        return "redirect:/#map";
    }
}
