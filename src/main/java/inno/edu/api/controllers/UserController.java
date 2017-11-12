package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.MenteeProfileResource;
import inno.edu.api.controllers.resources.MentorProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.user.commands.CreateUserCommand;
import inno.edu.api.domain.user.commands.UpdateUserCommand;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.queries.GetMenteeProfileByUserIdQuery;
import inno.edu.api.domain.user.queries.GetMentorActiveProfileByUserIdQuery;
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

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/users", produces = "application/hal+json")
public class UserController {

    private final UserRepository userRepository;

    private final GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery;
    private final GetMenteeProfileByUserIdQuery getMenteeProfileByUserIdQuery;

    private final CreateUserCommand createUserCommand;
    private final UpdateUserCommand updateUserCommand;

    private final ResourceBuilder resourceBuilder;

    @Autowired
    public UserController(UserRepository userRepository, GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery, GetMenteeProfileByUserIdQuery getMenteeProfileByUserIdQuery, CreateUserCommand createUserCommand, UpdateUserCommand updateUserCommand, ResourceBuilder resourceBuilder) {
        this.userRepository = userRepository;
        this.getMentorActiveProfileByUserIdQuery = getMentorActiveProfileByUserIdQuery;
        this.getMenteeProfileByUserIdQuery = getMenteeProfileByUserIdQuery;
        this.createUserCommand = createUserCommand;
        this.updateUserCommand = updateUserCommand;
        this.resourceBuilder = resourceBuilder;
    }

    @GetMapping
    public Resources<UserResource> all() {
        Iterable<User> users = userRepository.findAll();
        return resourceBuilder.from(users, UserResource::new);
    }

    @GetMapping("/{id}")
    public UserResource get(@PathVariable UUID id) {
        Optional<User> user = ofNullable(userRepository.findOne(id));
        return new UserResource(user.orElseThrow(() -> new UserNotFoundException(id)));
    }

    @GetMapping("/{id}/profile")
    public ResourceSupport getProfile(@PathVariable UUID id) {
        if (userRepository.existsUserByIdAndIsMentorIsTrue(id)) {
            return new MentorProfileResource(getMentorActiveProfileByUserIdQuery.run(id));
        }
        return new MenteeProfileResource(getMenteeProfileByUserIdQuery.run(id));
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
}