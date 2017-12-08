package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.SchoolController;
import inno.edu.api.controllers.UserController;
import inno.edu.api.domain.profile.queries.dtos.MentorProfileUser;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
public class MentorProfileUserResource extends ResourceSupport {
    @JsonUnwrapped
    private final MentorProfileUser response;

    public MentorProfileUserResource(MentorProfileUser response) {
        this.response = response;

        add(linkTo(methodOn(UserController.class).get(response.getMentorId())).withRel("mentor"));
        add(linkTo(methodOn(SchoolController.class).get(response.getSchoolId())).withRel("school"));
    }
}
