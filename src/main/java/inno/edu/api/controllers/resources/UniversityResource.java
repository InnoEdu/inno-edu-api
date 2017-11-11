package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.AppointmentController;
import inno.edu.api.controllers.AvailabilityController;
import inno.edu.api.controllers.UniversityController;
import inno.edu.api.domain.university.models.University;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
public class UniversityResource extends ResourceSupport {
    @JsonUnwrapped
    private final University university;

    public UniversityResource(University university) {
        this.university = university;

        add(linkTo(methodOn(UniversityController.class).get(university.getId())).withSelfRel());
        add(linkTo(methodOn(AvailabilityController.class).allByUniversity(university.getId())).withRel("availability"));
        add(linkTo(methodOn(AppointmentController.class).allByUniversity(university.getId(), PROPOSED)).withRel("proposed-appointments"));
    }

    public ResponseEntity<?> toCreated() {
        return ResponseEntity.created(URI.create(getLink("self").getHref())).build();
    }

    public ResponseEntity<?> toUpdated() {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(university);
    }
}
