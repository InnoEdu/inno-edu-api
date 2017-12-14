package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.user.commands.LoginCommand;
import inno.edu.api.domain.user.commands.dtos.LoginRequest;
import inno.edu.api.domain.user.commands.dtos.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static inno.edu.api.infrastructure.security.SecurityConstants.HEADER_STRING;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/api/auth", produces = "application/hal+json")
public class AuthenticationController {
    private final LoginCommand loginCommand;

    public AuthenticationController(LoginCommand loginCommand) {
        this.loginCommand = loginCommand;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResource> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = loginCommand.run(request);
        
        return ok()
                .header(HEADER_STRING, loginResponse.getPrefixedToken())
                .body(new UserResource(loginResponse.getUser()));
    }
}
