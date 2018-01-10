package inno.edu.api.controllers.profile.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@Getter
public class ProfileAttachmentResource extends ResourceSupport {
    @JsonUnwrapped
    private final ProfileAttachment profileAttachment;

    public ProfileAttachmentResource(ProfileAttachment profileAttachment) {
        this.profileAttachment = profileAttachment;
    }
}
