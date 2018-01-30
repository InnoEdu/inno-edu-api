package inno.edu.api.controllers.profile;

import inno.edu.api.domain.attachment.models.resources.AttachmentResource;
import inno.edu.api.infrastructure.web.ResourceBuilder;
import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.profile.attachment.commands.CreateProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.commands.DeleteProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.commands.UploadProfilePhotoCommand;
import inno.edu.api.domain.profile.attachment.queries.GetProfileAttachmentsByProfileIdQuery;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static inno.edu.api.infrastructure.configuration.StaticConstants.APPLICATION_HAL_JSON;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/profiles", produces = APPLICATION_HAL_JSON)
public class ProfileAttachmentController {
    private final ResourceBuilder resourceBuilder;
    private final GetProfileAttachmentsByProfileIdQuery getProfileAttachmentsByProfileIdQuery;
    private final CreateProfileAttachmentCommand createProfileAttachmentCommand;
    private final DeleteProfileAttachmentCommand deleteProfileAttachmentCommand;
    private final UploadProfilePhotoCommand uploadPhotoAttachmentCommand;

    public ProfileAttachmentController(ResourceBuilder resourceBuilder, GetProfileAttachmentsByProfileIdQuery getProfileAttachmentsByProfileIdQuery, CreateProfileAttachmentCommand createProfileAttachmentCommand, DeleteProfileAttachmentCommand deleteProfileAttachmentCommand, UploadProfilePhotoCommand uploadPhotoAttachmentCommand) {
        this.resourceBuilder = resourceBuilder;
        this.getProfileAttachmentsByProfileIdQuery = getProfileAttachmentsByProfileIdQuery;
        this.createProfileAttachmentCommand = createProfileAttachmentCommand;
        this.deleteProfileAttachmentCommand = deleteProfileAttachmentCommand;
        this.uploadPhotoAttachmentCommand = uploadPhotoAttachmentCommand;
    }

    @GetMapping("/{profileId}/attachments")
    public Resources<Object> all(@PathVariable UUID profileId) {
        List<Attachment> attachments = getProfileAttachmentsByProfileIdQuery.run(profileId);
        return resourceBuilder.wrappedFrom(attachments, AttachmentResource::new, AttachmentResource.class);
    }

    @PostMapping("/{profileId}/attachments")
    public AttachmentResource post(@PathVariable UUID profileId, @RequestParam String description, @RequestParam MultipartFile file) {
        CreateAttachmentRequest request = CreateAttachmentRequest.builder()
                .file(file)
                .description(description)
                .build();

        return new AttachmentResource(createProfileAttachmentCommand.run(profileId, request));
    }

    @PostMapping("/{profileId}/upload-photo")
    public AttachmentResource upload(@PathVariable UUID profileId, @RequestParam MultipartFile file) {
        return new AttachmentResource(uploadPhotoAttachmentCommand.run(profileId, file));
    }

    @DeleteMapping("/{profileId}/attachments/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID profileId, @PathVariable UUID id) {
        deleteProfileAttachmentCommand.run(profileId, id);
        return noContent().build();
    }
}
