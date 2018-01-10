package inno.edu.api.controllers.profile;

import inno.edu.api.domain.profile.attachment.commands.UploadProfileContentCommand;
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

    private final UploadProfileContentCommand uploadProfileContentCommand;

    public ProfileAttachmentController(UploadProfileContentCommand uploadProfileContentCommand) {
        this.uploadProfileContentCommand = uploadProfileContentCommand;
    }

    @PostMapping(value = "/{id}/upload")
    public ResponseEntity upload(@PathVariable UUID id, @RequestParam MultipartFile file) {
        uploadProfileContentCommand.run(id, file);
        return noContent().build();
    }
}
