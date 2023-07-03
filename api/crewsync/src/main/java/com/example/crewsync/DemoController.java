package com.example.crewsync;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/test")
    public String demo() {
        return "test";
    }
}

