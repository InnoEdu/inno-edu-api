package inno.edu.api.controllers.school;

import inno.edu.api.domain.attachment.models.resources.AttachmentResource;
import inno.edu.api.infrastructure.web.ResourceBuilder;
import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.school.attachment.commands.CreateSchoolAttachmentCommand;
import inno.edu.api.domain.school.attachment.commands.DeleteSchoolAttachmentCommand;
import inno.edu.api.domain.school.attachment.queries.GetSchoolAttachmentsBySchoolIdQuery;
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

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/schools", produces = "application/hal+json")
public class SchoolAttachmentController {
    private final ResourceBuilder resourceBuilder;
    private final GetSchoolAttachmentsBySchoolIdQuery getSchoolAttachmentsBySchoolIdQuery;
    private final CreateSchoolAttachmentCommand createSchoolAttachmentCommand;
    private final DeleteSchoolAttachmentCommand deleteSchoolAttachmentCommand;

    public SchoolAttachmentController(ResourceBuilder resourceBuilder, GetSchoolAttachmentsBySchoolIdQuery getSchoolAttachmentsBySchoolIdQuery, CreateSchoolAttachmentCommand createSchoolAttachmentCommand, DeleteSchoolAttachmentCommand deleteSchoolAttachmentCommand) {
        this.resourceBuilder = resourceBuilder;
        this.getSchoolAttachmentsBySchoolIdQuery = getSchoolAttachmentsBySchoolIdQuery;
        this.createSchoolAttachmentCommand = createSchoolAttachmentCommand;
        this.deleteSchoolAttachmentCommand = deleteSchoolAttachmentCommand;
    }

    @GetMapping("/{schoolId}/attachments")
    public Resources<Object> all(@PathVariable UUID schoolId) {
        List<Attachment> attachments = getSchoolAttachmentsBySchoolIdQuery.run(schoolId);
        return resourceBuilder.wrappedFrom(attachments, AttachmentResource::new, AttachmentResource.class);
    }

    @PostMapping("/{schoolId}/attachments")
    public AttachmentResource post(@PathVariable UUID schoolId, @RequestParam String description, @RequestParam MultipartFile file) {
        CreateAttachmentRequest request = CreateAttachmentRequest.builder()
                .file(file)
                .description(description)
                .build();

        return new AttachmentResource(createSchoolAttachmentCommand.run(schoolId, request));
    }

    @DeleteMapping("/{schoolId}/attachments/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID schoolId, @PathVariable UUID id) {
        deleteSchoolAttachmentCommand.run(schoolId, id);
        return noContent().build();
    }
}
