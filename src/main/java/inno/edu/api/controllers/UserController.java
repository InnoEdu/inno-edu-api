package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.MenteeProfileResource;
import inno.edu.api.controllers.resources.MentorProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.profile.commands.UpdateMentorProfileStatusByUserCommand;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.queries.GetMenteeProfileByUserIdQuery;
import inno.edu.api.domain.profile.queries.GetMentorActiveProfileByUserIdQuery;
import inno.edu.api.domain.user.commands.CreateUserCommand;
import inno.edu.api.domain.user.commands.LoginCommand;
import inno.edu.api.domain.user.commands.UpdateUserCommand;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.models.Login;
import inno.edu.api.domain.user.models.User;
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

import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/users", produces = "application/hal+json")
public class UserController {

    private final UserRepository userRepository;

    private final GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery;
    private final GetMenteeProfileByUserIdQuery getMenteeProfileByUserIdQuery;
    private final GetUserByIdQuery getUserByIdQuery;

    private final LoginCommand loginCommand;
    private final CreateUserCommand createUserCommand;
    private final UpdateUserCommand updateUserCommand;

    private final ResourceBuilder resourceBuilder;
    private final UpdateMentorProfileStatusByUserCommand updateMentorProfileStatusByUserCommand;

    @Autowired
    public UserController(UserRepository userRepository, GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery, GetMenteeProfileByUserIdQuery getMenteeProfileByUserIdQuery, GetUserByIdQuery getUserByIdQuery, LoginCommand loginCommand, CreateUserCommand createUserCommand, UpdateUserCommand updateUserCommand, ResourceBuilder resourceBuilder, UpdateMentorProfileStatusByUserCommand updateMentorProfileStatusByUserCommand) {
        this.userRepository = userRepository;
        this.getMentorActiveProfileByUserIdQuery = getMentorActiveProfileByUserIdQuery;
        this.getMenteeProfileByUserIdQuery = getMenteeProfileByUserIdQuery;
        this.getUserByIdQuery = getUserByIdQuery;
        this.loginCommand = loginCommand;
        this.createUserCommand = createUserCommand;
        this.updateUserCommand = updateUserCommand;
        this.resourceBuilder = resourceBuilder;
        this.updateMentorProfileStatusByUserCommand = updateMentorProfileStatusByUserCommand;
    }

    @GetMapping
    public Resources<UserResource> all() {
        Iterable<User> users = userRepository.findAll();
        return resourceBuilder.from(users, UserResource::new);
    }

    @GetMapping("/{id}")
    public UserResource get(@PathVariable UUID id) {
        return new UserResource(getUserByIdQuery.run(id));
    }

    @GetMapping("/{id}/profile")
    public ResourceSupport getProfile(@PathVariable UUID id) {
        if (userRepository.existsByIdAndIsMentorIsTrue(id)) {
            return new MentorProfileResource(getMentorActiveProfileByUserIdQuery.run(id));
        }
        return new MenteeProfileResource(getMenteeProfileByUserIdQuery.run(id));
    }

    @PostMapping("/login")
    public UserResource login(@RequestBody Login login) {
        return new UserResource(loginCommand.run(login));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody User user) {
        UserResource userResource = new UserResource(createUserCommand.run(user));
        return userResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable UUID id, @RequestBody User user) {
        UserResource userResource = new UserResource(updateUserCommand.run(id, user));
        return userResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        if (!userRepository.exists(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.delete(id);

        return noContent().build();
    }

    @PutMapping("/{id}/approve")
    public  ResponseEntity<?>  approve(@PathVariable UUID id) {
        updateMentorProfileStatusByUserCommand.run(id, ProfileStatus.ACTIVE);
        return noContent().build();
    }

    @PutMapping("/{id}/reject")
    public  ResponseEntity<?>  reject(@PathVariable UUID id) {
        updateMentorProfileStatusByUserCommand.run(id, ProfileStatus.REJECTED);
        return noContent().build();
    }

}