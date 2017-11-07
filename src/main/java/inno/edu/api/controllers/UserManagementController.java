package inno.edu.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class UserManagementController {
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}