package inno.edu.api.controllers.resources;

import inno.edu.api.controllers.UserController;
import inno.edu.api.domain.user.models.User;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
public class UserResource extends ResourceSupport {
    private final User user;

    public UserResource(User user) {
        this.user = user;
        add(linkTo(methodOn(UserController.class).get(user.getId())).withSelfRel());
    }
}
