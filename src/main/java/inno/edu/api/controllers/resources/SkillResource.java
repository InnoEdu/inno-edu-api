package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.SkillController;
import inno.edu.api.domain.skill.models.Skill;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class SkillResource extends ResourceSupport {
    @JsonUnwrapped
    private final Skill skill;

    public SkillResource(Skill skill) {
        this.skill = skill;

        add(ControllerLinkBuilder.linkTo(methodOn(SkillController.class).get(skill.getId())).withSelfRel());
    }

    public ResponseEntity<Skill> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(skill);
    }

    public ResponseEntity<Skill> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(skill);
    }
}
