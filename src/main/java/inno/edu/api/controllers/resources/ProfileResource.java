package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.ProfileController;
import inno.edu.api.controllers.UserController;
import inno.edu.api.domain.profile.models.Profile;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class ProfileResource extends ResourceSupport {
    @JsonUnwrapped
    private final Profile profile;

    public ProfileResource(Profile profile) {
        this.profile = profile;

        add(linkTo(methodOn(ProfileController.class).get(profile.getId())).withSelfRel());
        add(linkTo(methodOn(UserController.class).get(profile.getUserId())).withRel("user"));
    }

    public ResponseEntity<Profile> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(profile);
    }

    public ResponseEntity<Profile> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(profile);
    }
}
