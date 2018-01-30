package inno.edu.api.controllers;

import inno.edu.api.domain.attachment.models.resources.AttachmentResource;
import inno.edu.api.infrastructure.web.ResourceBuilder;
import inno.edu.api.domain.attachment.commands.CreateAttachmentCommand;
import inno.edu.api.domain.attachment.commands.DeleteAttachmentCommand;
import inno.edu.api.domain.attachment.commands.UpdateAttachmentCommand;
import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.dtos.UpdateAttachmentRequest;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.queries.GetAttachmentByIdQuery;
import inno.edu.api.domain.attachment.queries.GetAttachmentsQuery;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static inno.edu.api.infrastructure.configuration.StaticConstants.APPLICATION_HAL_JSON;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/attachments", produces = APPLICATION_HAL_JSON)
public class AttachmentController {
    private final ResourceBuilder resourceBuilder;

    private final GetAttachmentsQuery getAttachmentsQuery;
    private final GetAttachmentByIdQuery getAttachmentByIdQuery;

    private final CreateAttachmentCommand createAttachmentCommand;
    private final DeleteAttachmentCommand deleteAttachmentCommand;
    private final UpdateAttachmentCommand updateAttachmentCommand;

    public AttachmentController(ResourceBuilder resourceBuilder, GetAttachmentsQuery getAttachmentsQuery, GetAttachmentByIdQuery getAttachmentByIdQuery, CreateAttachmentCommand createAttachmentCommand, DeleteAttachmentCommand deleteAttachmentCommand, UpdateAttachmentCommand updateAttachmentCommand) {
        this.resourceBuilder = resourceBuilder;
        this.getAttachmentsQuery = getAttachmentsQuery;
        this.getAttachmentByIdQuery = getAttachmentByIdQuery;
        this.createAttachmentCommand = createAttachmentCommand;
        this.deleteAttachmentCommand = deleteAttachmentCommand;
        this.updateAttachmentCommand = updateAttachmentCommand;
    }

    @GetMapping
    public Resources<Object> all() {
        List<Attachment> attachments = getAttachmentsQuery.run();
        return resourceBuilder.wrappedFrom(attachments, AttachmentResource::new, AttachmentResource.class);
    }

    @GetMapping("/{id}")
    public AttachmentResource get(@PathVariable UUID id) {
        return new AttachmentResource(getAttachmentByIdQuery.run(id));
    }

    @PostMapping
    public AttachmentResource post(@RequestParam String description, @RequestParam MultipartFile file) {
        CreateAttachmentRequest request = CreateAttachmentRequest.builder()
                .file(file)
                .description(description)
                .build();

        return new AttachmentResource(createAttachmentCommand.run(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attachment> put(@PathVariable UUID id, @Valid @RequestBody UpdateAttachmentRequest request) {
        AttachmentResource attachmentResource = new AttachmentResource(updateAttachmentCommand.run(id, request));
        return attachmentResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteAttachmentCommand.run(id);
        return noContent().build();
    }
}
