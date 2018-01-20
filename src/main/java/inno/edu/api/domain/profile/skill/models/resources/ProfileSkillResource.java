package inno.edu.api.domain.profile.skill.models.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.profile.ProfileController;
import inno.edu.api.domain.profile.skill.models.ProfileSkill;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class ProfileSkillResource extends ResourceSupport {
    @JsonUnwrapped
    private final ProfileSkill profileSkill;

    public ProfileSkillResource(ProfileSkill profileSkill) {
        this.profileSkill = profileSkill;

        add(linkTo(methodOn(ProfileController.class).get(profileSkill.getProfileId())).withRel("profile"));
        add(linkTo(methodOn(ProfileController.class).get(profileSkill.getSkillId())).withRel("skill"));
    }

    public ResponseEntity<ProfileSkill> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(profileSkill);
    }

    public ResponseEntity<ProfileSkill> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(profileSkill);
    }
}
