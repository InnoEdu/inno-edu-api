package inno.edu.api.controllers.profile;

import inno.edu.api.domain.profile.attachment.commands.UploadProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.commands.dtos.UploadProfileAttachmentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/profiles", produces = "application/hal+json")
public class ProfileAttachmentController {

    private final UploadProfileAttachmentCommand uploadProfileContentCommand;

    public ProfileAttachmentController(UploadProfileAttachmentCommand uploadProfileContentCommand) {
        this.uploadProfileContentCommand = uploadProfileContentCommand;
    }

    @PostMapping(value = "/{id}/upload")
    public ResponseEntity upload(@PathVariable UUID id, @RequestParam String description, @RequestParam MultipartFile file) {
        UploadProfileAttachmentRequest request = UploadProfileAttachmentRequest.builder()
                .profileId(id)
                .file(file)
                .description(description)
                .build();

        uploadProfileContentCommand.run(request);
        return noContent().build();
    }
}
