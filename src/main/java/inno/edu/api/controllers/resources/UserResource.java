package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.AppointmentController;
import inno.edu.api.controllers.AvailabilityController;
import inno.edu.api.controllers.UserController;
import inno.edu.api.domain.user.models.User;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class UserResource extends ResourceSupport {

    @JsonUnwrapped
    private final User user;

    public UserResource(User user) {
        this.user = user;
        add(linkTo(methodOn(UserController.class).get(user.getId())).withSelfRel());
        add(linkTo(methodOn(UserController.class).getProfile(user.getId())).withRel("profile"));

        if (user.getIsMentor()) {
            add(linkTo(methodOn(AvailabilityController.class).allByMentor(user.getId())).withRel("availability"));
            add(linkTo(methodOn(AppointmentController.class).allByMentor(user.getId(), PROPOSED)).withRel("proposed-appointments"));
        } else {
            add(linkTo(methodOn(AppointmentController.class).allByMentee(user.getId(), PROPOSED)).withRel("proposed-appointments"));
        }
    }

    public ResponseEntity<?> toCreated() {
        return created(URI.create(getLink("self").getHref())).build();
    }

    public ResponseEntity<?> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(user);
    }
}
