package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.AppointmentController;
import inno.edu.api.controllers.MentorProfileController;
import inno.edu.api.controllers.UserController;
import inno.edu.api.domain.appointment.models.Appointment;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class AppointmentResource extends ResourceSupport {
    @JsonUnwrapped
    private final Appointment appointment;

    public AppointmentResource(Appointment appointment) {
        this.appointment = appointment;

        add(linkTo(methodOn(AppointmentController.class).get(appointment.getId())).withSelfRel());
        add(linkTo(methodOn(MentorProfileController.class).get(appointment.getMentorProfileId())).withRel("mentor-profile"));
        add(linkTo(methodOn(UserController.class).get(appointment.getMenteeId())).withRel("mentee"));
    }

    public ResponseEntity<?> toCreated() {
        return created(URI.create(getLink("self").getHref())).build();
    }

    public ResponseEntity<?> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(appointment);
    }
}
