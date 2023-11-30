package com.employee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class Test {
    
	@GetMapping("/pooja")
    public String getSample() {
        return "Hello from the /sample endpoint!";
    }

}
