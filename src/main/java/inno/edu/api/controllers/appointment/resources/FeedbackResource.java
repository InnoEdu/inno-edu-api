package inno.edu.api.controllers.appointment.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.appointment.FeedbackController;
import inno.edu.api.domain.appointment.feedback.models.Feedback;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.http.ResponseEntity.created;

@Getter
public class FeedbackResource extends ResourceSupport {
    @JsonUnwrapped
    private final Feedback feedback;

    public FeedbackResource(Feedback feedback) {
        this.feedback = feedback;

        add(ControllerLinkBuilder.linkTo(methodOn(FeedbackController.class).getFeedback(feedback.getId())).withSelfRel());
    }

    public ResponseEntity<Feedback> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(feedback);
    }
}
