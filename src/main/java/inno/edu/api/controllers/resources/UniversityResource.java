package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.AppointmentController;
import inno.edu.api.controllers.AvailabilityController;
import inno.edu.api.controllers.UniversityController;
import inno.edu.api.domain.university.models.University;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class UniversityResource extends ResourceSupport {
    @JsonUnwrapped
    private final University university;

    public UniversityResource(University university) {
        this.university = university;

        add(linkTo(methodOn(UniversityController.class).get(university.getId())).withSelfRel());
        add(linkTo(methodOn(UniversityController.class).allMentorsProfile(university.getId())).withRel("mentors-profile"));
        add(linkTo(methodOn(AvailabilityController.class).allByUniversity(university.getId())).withRel("availability"));
        add(linkTo(methodOn(AppointmentController.class).allByUniversity(university.getId(), PROPOSED)).withRel("proposed-appointments"));
    }

    public ResponseEntity<?> toCreated() {
        return created(URI.create(getLink("self").getHref())).build();
    }

    public ResponseEntity<?> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(university);
    }
}
