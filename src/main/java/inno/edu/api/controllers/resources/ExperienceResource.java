package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.ProfileController;
import inno.edu.api.domain.profile.experience.models.Experience;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class ExperienceResource extends ResourceSupport {
    @JsonUnwrapped
    private final Experience experience;

    public ExperienceResource(Experience experience) {
        this.experience = experience;

        add(linkTo(methodOn(ProfileController.class).getExperience(experience.getId())).withSelfRel());
        add(linkTo(methodOn(ProfileController.class).get(experience.getProfileId())).withRel("profile"));
    }

    public ResponseEntity<Experience> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(experience);
    }

    public ResponseEntity<Experience> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(experience);
    }
}
