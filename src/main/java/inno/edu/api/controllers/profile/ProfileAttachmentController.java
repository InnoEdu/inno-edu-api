package inno.edu.api.controllers.profile;

import inno.edu.api.controllers.profile.resources.ProfileAttachmentResource;
import inno.edu.api.domain.profile.attachment.commands.CreateProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.commands.DeleteProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.commands.dtos.CreateProfileAttachmentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/profiles/", produces = "application/hal+json")
public class ProfileAttachmentController {

    private final CreateProfileAttachmentCommand uploadProfileContentCommand;
    private final DeleteProfileAttachmentCommand deleteProfileAttachmentCommand;

    public ProfileAttachmentController(CreateProfileAttachmentCommand uploadProfileContentCommand, DeleteProfileAttachmentCommand deleteProfileAttachmentCommand) {
        this.uploadProfileContentCommand = uploadProfileContentCommand;
        this.deleteProfileAttachmentCommand = deleteProfileAttachmentCommand;
    }

    @PostMapping(value = "/{id}/attachments")
    public ProfileAttachmentResource upload(@PathVariable UUID id, @RequestParam String description, @RequestParam MultipartFile file) {
        CreateProfileAttachmentRequest request = CreateProfileAttachmentRequest.builder()
                .profileId(id)
                .file(file)
                .description(description)
                .build();

        return new ProfileAttachmentResource(uploadProfileContentCommand.run(request));
    }

    @DeleteMapping("/attachments/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteProfileAttachmentCommand.run(id);
        return noContent().build();
    }
}
