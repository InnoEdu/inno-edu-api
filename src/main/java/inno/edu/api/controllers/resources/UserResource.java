package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.AvailabilityController;
import inno.edu.api.controllers.UserController;
import inno.edu.api.domain.user.models.User;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
public class UserResource extends ResourceSupport {

    @JsonUnwrapped
    private final User user;

    public UserResource(User user) {
        this.user = user;
        add(linkTo(methodOn(UserController.class).get(user.getId())).withSelfRel());
        add(linkTo(methodOn(AvailabilityController.class).allByUser(user.getId())).withRel("availability"));
    }

    public ResponseEntity<?> toCreated() {
        return ResponseEntity.created(URI.create(getLink("self").getHref())).build();
    }

    public ResponseEntity<?> toUpdated() {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(user);
    }
}
