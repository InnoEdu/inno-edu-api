package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.domain.attachment.models.Attachment;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@Getter
public class AttachmentResource extends ResourceSupport {
    @JsonUnwrapped
    private final Attachment attachment;

    public AttachmentResource(Attachment attachment) {
        this.attachment = attachment;
    }
}
