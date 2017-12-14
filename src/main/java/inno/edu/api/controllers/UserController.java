package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.MenteeProfileResource;
import inno.edu.api.controllers.resources.MentorProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.profile.commands.UpdateMentorProfileStatusByUserCommand;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.queries.GetMenteeProfileByUserIdQuery;
import inno.edu.api.domain.profile.queries.GetMentorProfileByUserIdQuery;
import inno.edu.api.domain.user.commands.CreateUserCommand;
import inno.edu.api.domain.user.commands.DeleteUserCommand;
import inno.edu.api.domain.user.commands.UpdateUserCommand;
import inno.edu.api.domain.user.commands.dtos.CreateUserRequest;
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

@RestController
@RequestMapping(value = "/api/users", produces = "application/hal+json")
public class UserController {

    private final UserRepository userRepository;

    private final GetMentorProfileByUserIdQuery getMentorProfileByUserIdQuery;
    private final GetMenteeProfileByUserIdQuery getMenteeProfileByUserIdQuery;
    private final GetUserByIdQuery getUserByIdQuery;

    private final CreateUserCommand createUserCommand;
    private final UpdateUserCommand updateUserCommand;
    private final DeleteUserCommand deleteUserCommand;

    private final ResourceBuilder resourceBuilder;
    private final UpdateMentorProfileStatusByUserCommand updateMentorProfileStatusByUserCommand;

    @Autowired
    public UserController(UserRepository userRepository, GetMentorProfileByUserIdQuery getMentorProfileByUserIdQuery, GetMenteeProfileByUserIdQuery getMenteeProfileByUserIdQuery, GetUserByIdQuery getUserByIdQuery, CreateUserCommand createUserCommand, UpdateUserCommand updateUserCommand, DeleteUserCommand deleteUserCommand, ResourceBuilder resourceBuilder, UpdateMentorProfileStatusByUserCommand updateMentorProfileStatusByUserCommand) {
        this.userRepository = userRepository;
        this.getMentorProfileByUserIdQuery = getMentorProfileByUserIdQuery;
        this.getMenteeProfileByUserIdQuery = getMenteeProfileByUserIdQuery;
        this.getUserByIdQuery = getUserByIdQuery;
        this.createUserCommand = createUserCommand;
        this.updateUserCommand = updateUserCommand;
        this.deleteUserCommand = deleteUserCommand;
        this.resourceBuilder = resourceBuilder;
        this.updateMentorProfileStatusByUserCommand = updateMentorProfileStatusByUserCommand;
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
        if (userRepository.existsByIdAndIsMentorIsTrue(id)) {
            return new MentorProfileResource(getMentorProfileByUserIdQuery.run(id));
        }
        return new MenteeProfileResource(getMenteeProfileByUserIdQuery.run(id));
    }

    @PostMapping
    public ResponseEntity<ApplicationUser> post(@Valid @RequestBody CreateUserRequest request) {
        UserResource userResource = new UserResource(createUserCommand.run(request));
        return userResource.toCreated();
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