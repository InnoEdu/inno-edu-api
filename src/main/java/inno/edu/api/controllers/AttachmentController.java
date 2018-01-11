package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AttachmentResource;
import inno.edu.api.domain.attachment.commands.CreateAttachmentCommand;
import inno.edu.api.domain.attachment.commands.DeleteAttachmentCommand;
import inno.edu.api.domain.attachment.commands.dtos.CreateAttachmentRequest;
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
@RequestMapping(value = "/api/attachments/", produces = "application/hal+json")
public class AttachmentController {

    private final CreateAttachmentCommand uploadProfileContentCommand;
    private final DeleteAttachmentCommand deleteProfileAttachmentCommand;

    public AttachmentController(CreateAttachmentCommand uploadProfileContentCommand, DeleteAttachmentCommand deleteProfileAttachmentCommand) {
        this.uploadProfileContentCommand = uploadProfileContentCommand;
        this.deleteProfileAttachmentCommand = deleteProfileAttachmentCommand;
    }

    @PostMapping
    public AttachmentResource post(@RequestParam String description, @RequestParam MultipartFile file) {
        CreateAttachmentRequest request = CreateAttachmentRequest.builder()
                .file(file)
                .description(description)
                .build();

        return new AttachmentResource(uploadProfileContentCommand.run(request));
    }

    @DeleteMapping("/attachments/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteProfileAttachmentCommand.run(id);
        return noContent().build();
    }
}
