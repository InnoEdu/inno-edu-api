package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.AppointmentController;
import inno.edu.api.controllers.AvailabilityController;
import inno.edu.api.controllers.UserController;
import inno.edu.api.domain.user.models.User;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
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
        add(linkTo(methodOn(AppointmentController.class).allByMentor(user.getId(), PROPOSED)).withRel("mentor-proposed-appointments"));
        add(linkTo(methodOn(AppointmentController.class).allByMentee(user.getId(), PROPOSED)).withRel("mentee-proposed-appointments"));
    }

    public ResponseEntity<?> toCreated() {
        return ResponseEntity.created(URI.create(getLink("self").getHref())).build();
    }

    public ResponseEntity<?> toUpdated() {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(user);
    }
}
