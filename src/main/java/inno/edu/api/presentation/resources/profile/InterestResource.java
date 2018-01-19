package inno.edu.api.presentation.resources.profile;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.profile.InterestController;
import inno.edu.api.controllers.profile.ProfileController;
import inno.edu.api.domain.profile.interest.models.Interest;
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
public class InterestResource extends ResourceSupport {
    @JsonUnwrapped
    private final Interest interest;

    public InterestResource(Interest interest) {
        this.interest = interest;

        add(ControllerLinkBuilder.linkTo(methodOn(InterestController.class).get(interest.getId())).withSelfRel());
        add(ControllerLinkBuilder.linkTo(methodOn(ProfileController.class).get(interest.getProfileId())).withRel("profile"));
    }

    public ResponseEntity<Interest> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(interest);
    }

    public ResponseEntity<Interest> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(interest);
    }
}
