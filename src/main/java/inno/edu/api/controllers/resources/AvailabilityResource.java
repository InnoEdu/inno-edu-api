package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.AvailabilityController;
import inno.edu.api.domain.availability.models.Availability;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
public class AvailabilityResource extends ResourceSupport {
    @JsonUnwrapped
    private final Availability availability;

    public AvailabilityResource(Availability availability) {
        this.availability = availability;
        add(linkTo(methodOn(AvailabilityController.class).get(availability.getId())).withSelfRel());
    }

    public ResponseEntity<?> toCreated() {
        return ResponseEntity.created(URI.create(getLink("self").getHref())).build();
    }

    public ResponseEntity<?> toUpdated() {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(availability);
    }
}
