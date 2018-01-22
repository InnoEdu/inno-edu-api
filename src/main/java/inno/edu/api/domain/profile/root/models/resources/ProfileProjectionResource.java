package inno.edu.api.domain.profile.root.models.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.UserController;
import inno.edu.api.controllers.profile.ProfileController;
import inno.edu.api.domain.profile.root.models.projections.ProfileProjection;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
public class ProfileProjectionResource extends ResourceSupport {
    @JsonUnwrapped
    private final ProfileProjection profile;

    public ProfileProjectionResource() {
        profile = null;
    }

    public ProfileProjectionResource(ProfileProjection profileProjection) {
        this.profile = profileProjection;

        add(linkTo(methodOn(ProfileController.class).get(profileProjection.getId())).withSelfRel());
        add(linkTo(methodOn(UserController.class).get(profileProjection.getUser().getId())).withRel("user"));
    }
}
