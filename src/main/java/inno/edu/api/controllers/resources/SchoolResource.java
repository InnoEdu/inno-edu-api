package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.SchoolController;
import inno.edu.api.domain.school.models.School;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class SchoolResource extends ResourceSupport {
    @JsonUnwrapped
    private final School school;

    public SchoolResource(School school) {
        this.school = school;

        add(linkTo(methodOn(SchoolController.class).get(school.getId())).withSelfRel());
        add(linkTo(methodOn(SchoolController.class).allMentorsProfile(school.getId())).withRel("mentors"));
    }

    public ResponseEntity<?> toCreated() {
        return created(URI.create(getLink("self").getHref())).build();
    }

    public ResponseEntity<?> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(school);
    }
}
