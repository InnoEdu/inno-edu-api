package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.collect.Streams.stream;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/api/users", produces = "application/hal+json")
public class UserController {

    private final UserRepository userRepository;
    private final ResourceBuilder resourceBuilder;

    @Autowired
    public UserController(UserRepository userRepository, ResourceBuilder resourceBuilder) {
        this.userRepository = userRepository;
        this.resourceBuilder = resourceBuilder;
    }

    @GetMapping
    @ResponseStatus(OK)
    public Resources<UserResource> all() {
        List<UserResource> users = stream(userRepository.findAll())
                .map(UserResource::new)
                .collect(toList());

        return resourceBuilder.fromResources(users);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public UserResource get(@PathVariable UUID id) {
        Optional<User> user = ofNullable(userRepository.findOne(id));
        return new UserResource(user.orElseThrow(() -> new UserNotFoundException(valueOf(id))));
    }

}