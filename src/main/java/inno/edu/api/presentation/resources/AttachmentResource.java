package inno.edu.api.presentation.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.AttachmentController;
import inno.edu.api.domain.attachment.models.Attachment;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class AttachmentResource extends ResourceSupport {
    @JsonUnwrapped
    private final Attachment attachment;

    public AttachmentResource(Attachment attachment) {
        this.attachment = attachment;

        add(ControllerLinkBuilder.linkTo(methodOn(AttachmentController.class).get(attachment.getId())).withSelfRel());
    }

    public ResponseEntity<Attachment> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(attachment);
    }

    public ResponseEntity<Attachment> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(attachment);
    }
}
