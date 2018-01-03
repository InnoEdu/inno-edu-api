package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.AvailabilityController;
import inno.edu.api.controllers.ProfileController;
import inno.edu.api.domain.availability.models.Availability;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class AvailabilityResource extends ResourceSupport {
    @JsonUnwrapped
    private final Availability availability;

    public AvailabilityResource(Availability availability) {
        this.availability = availability;

        add(linkTo(methodOn(AvailabilityController.class).get(availability.getId())).withSelfRel());
        add(linkTo(methodOn(ProfileController.class).get(availability.getMentorProfileId())).withRel("profile"));
    }

    public ResponseEntity<Availability> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(availability);
    }

    public ResponseEntity<Availability> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(availability);
    }
}
