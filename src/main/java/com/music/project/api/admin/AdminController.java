package com.music.project.api.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
    @RequestMapping("api/admin")
public class AdminController {

    @GetMapping(value = "test")
    public String test() {
        return "ok";
    }




}
