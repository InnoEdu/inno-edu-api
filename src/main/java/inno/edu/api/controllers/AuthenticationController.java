package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.user.commands.LoginCommand;
import inno.edu.api.domain.user.commands.dtos.LoginRequest;
import inno.edu.api.domain.user.commands.dtos.LoginResponse;
import inno.edu.api.infrastructure.security.jwt.SecurityConstants;
import inno.edu.api.infrastructure.security.service.TokenGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/api/auth", produces = "application/hal+json")
public class AuthenticationController {
    private final LoginCommand loginCommand;
    private final TokenGeneratorService tokenGeneratorService;

    public AuthenticationController(LoginCommand loginCommand, TokenGeneratorService tokenGeneratorService) {
        this.loginCommand = loginCommand;
        this.tokenGeneratorService = tokenGeneratorService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResource> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = loginCommand.run(request);

        String token = tokenGeneratorService.generate(loginResponse.getUser().getUsername());
        return ok()
                .header(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token)
                .body(new UserResource(loginResponse.getUser()));
    }
}
