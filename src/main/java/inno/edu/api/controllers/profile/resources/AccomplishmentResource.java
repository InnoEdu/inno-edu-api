package inno.edu.api.controllers.profile.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.profile.AccomplishmentController;
import inno.edu.api.controllers.profile.ProfileController;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class AccomplishmentResource extends ResourceSupport {
    @JsonUnwrapped
    private final Accomplishment accomplishment;

    public AccomplishmentResource(Accomplishment accomplishment) {
        this.accomplishment = accomplishment;

        add(linkTo(methodOn(AccomplishmentController.class).get(accomplishment.getId())).withSelfRel());
        add(linkTo(methodOn(ProfileController.class).get(accomplishment.getProfileId())).withRel("profile"));
    }

    public ResponseEntity<Accomplishment> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(accomplishment);
    }

    public ResponseEntity<Accomplishment> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(accomplishment);
    }
}
