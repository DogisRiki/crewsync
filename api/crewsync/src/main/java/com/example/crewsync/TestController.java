package com.example.crewsync;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("aaa")
    public String aaa() {
        return "aaaaaa";
    }
}
