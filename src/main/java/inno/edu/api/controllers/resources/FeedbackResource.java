package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.AppointmentController;
import inno.edu.api.domain.appointment.models.Feedback;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;

@Getter
public class FeedbackResource extends ResourceSupport {
    @JsonUnwrapped
    private final Feedback feedback;

    public FeedbackResource(Feedback feedback) {
        this.feedback = feedback;

        add(linkTo(methodOn(AppointmentController.class).get(feedback.getId())).withSelfRel());
    }

    public ResponseEntity<Feedback> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(feedback);
    }
}
