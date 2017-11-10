package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.AppointmentController;
import inno.edu.api.controllers.UniversityController;
import inno.edu.api.controllers.UserController;
import inno.edu.api.domain.appointment.models.Appointment;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
public class AppointmentResource extends ResourceSupport {
    @JsonUnwrapped
    private final Appointment appointment;

    public AppointmentResource(Appointment appointment) {
        this.appointment = appointment;

        add(linkTo(methodOn(AppointmentController.class).get(appointment.getId())).withSelfRel());
        add(linkTo(methodOn(UserController.class).get(appointment.getMentorId())).withRel("mentor"));
        add(linkTo(methodOn(UserController.class).get(appointment.getMenteeId())).withRel("mentee"));
        add(linkTo(methodOn(UniversityController.class).get(appointment.getUniversityId())).withRel("university"));
    }

    public ResponseEntity<?> toCreated() {
        return ResponseEntity.created(URI.create(getLink("self").getHref())).build();
    }

    public ResponseEntity<?> toUpdated() {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(appointment);
    }
}
