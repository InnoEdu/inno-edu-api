package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.MenteeProfileController;
import inno.edu.api.controllers.UserController;
import inno.edu.api.domain.profile.models.MenteeProfile;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class MenteeProfileResource extends ResourceSupport {
    @JsonUnwrapped
    private final MenteeProfile menteeProfile;

    public MenteeProfileResource(MenteeProfile menteeProfile) {
        this.menteeProfile = menteeProfile;
        add(linkTo(methodOn(MenteeProfileController.class).get(menteeProfile.getId())).withSelfRel());
        add(linkTo(methodOn(UserController.class).get(menteeProfile.getMenteeId())).withRel("mentee"));
    }

    public ResponseEntity<?> toCreated() {
        return created(URI.create(getLink("self").getHref())).build();
    }

    public ResponseEntity<?> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(menteeProfile);
    }
}
