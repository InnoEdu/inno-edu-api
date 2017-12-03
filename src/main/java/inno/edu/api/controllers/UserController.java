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
import inno.edu.api.domain.user.commands.LoginCommand;
import inno.edu.api.domain.user.commands.UpdateUserCommand;
import inno.edu.api.domain.user.commands.dtos.CreateUserRequest;
import inno.edu.api.domain.user.commands.dtos.LoginRequest;
import inno.edu.api.domain.user.commands.dtos.UpdateUserRequest;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.queries.GetUserByIdQuery;
import inno.edu.api.domain.user.repositories.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
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

    private final LoginCommand loginCommand;
    private final CreateUserCommand createUserCommand;
    private final UpdateUserCommand updateUserCommand;
    private final DeleteUserCommand deleteUserCommand;

    private final ResourceBuilder resourceBuilder;
    private final UpdateMentorProfileStatusByUserCommand updateMentorProfileStatusByUserCommand;

    @Autowired
    public UserController(UserRepository userRepository, GetMentorProfileByUserIdQuery getMentorProfileByUserIdQuery, GetMenteeProfileByUserIdQuery getMenteeProfileByUserIdQuery, GetUserByIdQuery getUserByIdQuery, LoginCommand loginCommand, CreateUserCommand createUserCommand, UpdateUserCommand updateUserCommand, DeleteUserCommand deleteUserCommand, ResourceBuilder resourceBuilder, UpdateMentorProfileStatusByUserCommand updateMentorProfileStatusByUserCommand) {
        this.userRepository = userRepository;
        this.getMentorProfileByUserIdQuery = getMentorProfileByUserIdQuery;
        this.getMenteeProfileByUserIdQuery = getMenteeProfileByUserIdQuery;
        this.getUserByIdQuery = getUserByIdQuery;
        this.loginCommand = loginCommand;
        this.createUserCommand = createUserCommand;
        this.updateUserCommand = updateUserCommand;
        this.deleteUserCommand = deleteUserCommand;
        this.resourceBuilder = resourceBuilder;
        this.updateMentorProfileStatusByUserCommand = updateMentorProfileStatusByUserCommand;
    }

    @GetMapping
    @ApiOperation(value = "Find all users", notes = "Return all users.", response = User.class, responseContainer = "List")
    public Resources<UserResource> all() {
        Iterable<User> users = userRepository.findAll();
        return resourceBuilder.from(users, UserResource::new);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an user", notes = "Get an user by ID.", response = User.class)
    @ApiResponses({
            @ApiResponse(code = 404, message = "User not found."),
    })
    public UserResource get(@PathVariable UUID id) {
        return new UserResource(getUserByIdQuery.run(id));
    }

    @GetMapping("/{id}/profile")
    @ApiOperation(value = "Get user profile.", notes = "Get the Mentor or Mentee profile based on the user attributes.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 400, message = "User profile not found."),
    })
    public ResourceSupport getProfile(@PathVariable UUID id) {
        if (userRepository.existsByIdAndIsMentorIsTrue(id)) {
            return new MentorProfileResource(getMentorProfileByUserIdQuery.run(id));
        }
        return new MenteeProfileResource(getMenteeProfileByUserIdQuery.run(id));
    }

    @PostMapping("/login")
    @ApiOperation(value = "Validate user credentials.", notes = "Validate the user name and user password supplied.")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid credentials supplied."),
    })
    public UserResource login(@RequestBody LoginRequest loginRequest) {
        return new UserResource(loginCommand.run(loginRequest));
    }

    @PostMapping
    @ApiOperation(value = "Create a new user", notes = "Creates a new user.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "New user successfully created.", responseHeaders = @ResponseHeader(name = "Location", description = "Link to the new resource created.", response = String.class)),
            @ApiResponse(code = 400, message = "Username already exists or the passwords don't match."),
    })
    public ResponseEntity<User> post(@Valid @RequestBody CreateUserRequest createUserRequest) {
        UserResource userResource = new UserResource(createUserCommand.run(createUserRequest));
        return userResource.toCreated();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an user", notes = "Update an user.", response = User.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "New user successfully updated.", responseHeaders = @ResponseHeader(name = "Location", description = "Link to the updated resource.", response = String.class)),
            @ApiResponse(code = 404, message = "User not found."),
    })
    public ResponseEntity<User> put(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        UserResource userResource = new UserResource(updateUserCommand.run(id, updateUserRequest));
        return userResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an user", notes = "Delete an user, this operation cannot be undone.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "User successfully deleted."),
            @ApiResponse(code = 404, message = "User not found.")
    })
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteUserCommand.run(id);
        return noContent().build();
    }

    @PutMapping("/{id}/approve")
    @ApiOperation(value = "Approve a mentor", notes = "Approve the current created mentor profile for the user.", response = User.class)
    @ApiResponses({
            @ApiResponse(code = 204, message = "Mentor is approved."),
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 400, message = "User is not a mentor."),
    })
    public  ResponseEntity<?>  approve(@PathVariable UUID id) {
        updateMentorProfileStatusByUserCommand.run(id, ProfileStatus.ACTIVE);
        return noContent().build();
    }

    @PutMapping("/{id}/reject")
    @ApiOperation(value = "Reject a mentor", notes = "Reject the current created mentor profile for the user.", response = User.class)
    @ApiResponses({
            @ApiResponse(code = 204, message = "Mentor is rejected."),
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 400, message = "User is not a mentor."),
    })
    public  ResponseEntity<?>  reject(@PathVariable UUID id) {
        updateMentorProfileStatusByUserCommand.run(id, ProfileStatus.REJECTED);
        return noContent().build();
    }
}