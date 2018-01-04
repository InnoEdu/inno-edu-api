package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.UserController;
import inno.edu.api.domain.user.models.ApplicationUser;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class UserResource extends ResourceSupport {

    @JsonUnwrapped
    private final ApplicationUser user;

    public UserResource(ApplicationUser user) {
        this.user = user;

        add(linkTo(methodOn(UserController.class).get(user.getId())).withSelfRel());
        add(linkTo(methodOn(UserController.class).getProfile(user.getId())).withRel("profile"));
    }

    public ResponseEntity<ApplicationUser> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(user);
    }

    public ResponseEntity<ApplicationUser> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(user);
    }
}
