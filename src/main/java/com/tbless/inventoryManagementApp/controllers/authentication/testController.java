package com.tbless.inventoryManagementApp.controllers.authentication;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin (origins = "*")
public class testController {

    @GetMapping("/test")
    public String test(){
        return "This is just a test";
    }
}
