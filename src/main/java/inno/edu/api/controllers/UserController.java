package inno.edu.api.controllers;

import inno.edu.api.controllers.profile.resources.ProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.profile.root.queries.GetProfileByUserIdQuery;
import inno.edu.api.domain.user.commands.CreateUserCommand;
import inno.edu.api.domain.user.commands.DeleteUserCommand;
import inno.edu.api.domain.user.commands.LoginCommand;
import inno.edu.api.domain.user.commands.UpdateUserCommand;
import inno.edu.api.domain.user.commands.dtos.CreateUserRequest;
import inno.edu.api.domain.user.commands.dtos.LoginRequest;
import inno.edu.api.domain.user.commands.dtos.LoginResponse;
import inno.edu.api.domain.user.commands.dtos.UpdateUserRequest;
import inno.edu.api.domain.user.models.ApplicationUser;
import inno.edu.api.domain.user.queries.GetUserByIdQuery;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/api/users", produces = "application/hal+json")
public class UserController {

    private final UserRepository userRepository;

    private final GetProfileByUserIdQuery getProfileByUserIdQuery;
    private final GetUserByIdQuery getUserByIdQuery;

    private final CreateUserCommand createUserCommand;
    private final UpdateUserCommand updateUserCommand;
    private final DeleteUserCommand deleteUserCommand;
    private final LoginCommand loginCommand;

    private final ResourceBuilder resourceBuilder;

    @Autowired
    public UserController(UserRepository userRepository, GetProfileByUserIdQuery getProfileByUserIdQuery, GetUserByIdQuery getUserByIdQuery, CreateUserCommand createUserCommand, UpdateUserCommand updateUserCommand, DeleteUserCommand deleteUserCommand, LoginCommand loginCommand, ResourceBuilder resourceBuilder) {
        this.userRepository = userRepository;
        this.getProfileByUserIdQuery = getProfileByUserIdQuery;
        this.getUserByIdQuery = getUserByIdQuery;
        this.createUserCommand = createUserCommand;
        this.updateUserCommand = updateUserCommand;
        this.deleteUserCommand = deleteUserCommand;
        this.loginCommand = loginCommand;
        this.resourceBuilder = resourceBuilder;
    }

    @GetMapping
    public Resources<Object> all() {
        Iterable<ApplicationUser> users = userRepository.findAll();
        return resourceBuilder.wrappedFrom(users, UserResource::new, UserResource.class);
    }

    @GetMapping("/{id}")
    public UserResource get(@PathVariable UUID id) {
        return new UserResource(getUserByIdQuery.run(id));
    }

    @GetMapping("/{id}/profile")
    public ResourceSupport getProfile(@PathVariable UUID id) {
        return new ProfileResource(getProfileByUserIdQuery.run(id));
    }

    @PostMapping
    public ResponseEntity<LoginResponse> post(@Valid @RequestBody CreateUserRequest request) {
        ApplicationUser user = createUserCommand.run(request);

        LoginRequest loginRequest = LoginRequest.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        return ok().body(loginCommand.run(loginRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationUser> put(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request) {
        UserResource userResource = new UserResource(updateUserCommand.run(id, request));
        return userResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteUserCommand.run(id);
        return noContent().build();
    }
}