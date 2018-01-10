package inno.edu.api.controllers.profile;

import inno.edu.api.controllers.profile.resources.ProfileAttachmentResource;
import inno.edu.api.domain.profile.attachment.commands.UploadProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.commands.dtos.UploadProfileAttachmentRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/profiles/", produces = "application/hal+json")
public class ProfileAttachmentController {

    private final UploadProfileAttachmentCommand uploadProfileContentCommand;

    public ProfileAttachmentController(UploadProfileAttachmentCommand uploadProfileContentCommand) {
        this.uploadProfileContentCommand = uploadProfileContentCommand;
    }

    @PostMapping(value = "/{id}/attachments")
    public ProfileAttachmentResource upload(@PathVariable UUID id, @RequestParam String description, @RequestParam MultipartFile file) {
        UploadProfileAttachmentRequest request = UploadProfileAttachmentRequest.builder()
                .profileId(id)
                .file(file)
                .description(description)
                .build();

        return new ProfileAttachmentResource(uploadProfileContentCommand.run(request));
    }
}
