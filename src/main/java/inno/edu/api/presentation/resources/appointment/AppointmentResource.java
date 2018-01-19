package inno.edu.api.presentation.resources.appointment;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.appointment.AppointmentController;
import inno.edu.api.controllers.appointment.FeedbackController;
import inno.edu.api.controllers.profile.ProfileController;
import inno.edu.api.domain.appointment.root.models.Appointment;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
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

        add(ControllerLinkBuilder.linkTo(methodOn(AppointmentController.class).get(appointment.getId())).withSelfRel());
        add(linkTo(methodOn(FeedbackController.class).allFeedbacks(appointment.getId())).withRel("feedbacks"));
        add(linkTo(methodOn(ProfileController.class).get(appointment.getMentorProfileId())).withRel("mentor"));
        add(linkTo(methodOn(ProfileController.class).get(appointment.getMenteeProfileId())).withRel("mentee"));
    }

    public ResponseEntity<Appointment> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(appointment);
    }

    public ResponseEntity<Appointment> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(appointment);
    }
}
