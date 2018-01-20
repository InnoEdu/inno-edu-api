package inno.edu.api.domain.appointment.root.models.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.appointment.AppointmentController;
import inno.edu.api.controllers.appointment.FeedbackController;
import inno.edu.api.controllers.profile.ProfileController;
import inno.edu.api.domain.appointment.root.models.projections.AppointmentProjection;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
public class AppointmentProjectionResource extends ResourceSupport {
    @JsonUnwrapped
    private final AppointmentProjection appointmentProjection;

    public AppointmentProjectionResource() {
        this.appointmentProjection = null;
    }

    public AppointmentProjectionResource(AppointmentProjection appointmentProjection) {
        this.appointmentProjection = appointmentProjection;

        add(ControllerLinkBuilder.linkTo(methodOn(AppointmentController.class).get(appointmentProjection.getId())).withSelfRel());
        add(linkTo(methodOn(FeedbackController.class).allFeedbacks(appointmentProjection.getId())).withRel("feedbacks"));
        add(linkTo(methodOn(ProfileController.class).get(appointmentProjection.getMentorProfileId())).withRel("mentor"));
        add(linkTo(methodOn(ProfileController.class).get(appointmentProjection.getMenteeProfileId())).withRel("mentee"));
    }
}
