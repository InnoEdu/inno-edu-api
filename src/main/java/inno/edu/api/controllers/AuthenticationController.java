package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.user.commands.LoginCommand;
import inno.edu.api.domain.user.commands.dtos.LoginRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController {
    private final LoginCommand loginCommand;

    public AuthenticationController(LoginCommand loginCommand) {
        this.loginCommand = loginCommand;
    }

    @PostMapping("/login")
    @ApiOperation(value = "Validate user credentials.", notes = "Validate the user name and user password supplied.")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid credentials supplied."),
    })
    public UserResource login(@RequestBody LoginRequest request) {
        return new UserResource(loginCommand.run(request));
    }

}
