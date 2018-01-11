package inno.edu.api.domain.attachment.queries;

import inno.edu.api.domain.attachment.exceptions.AttachmentNotFoundException;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.repositories.AttachmentRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetAttachmentByIdQuery {
    private final AttachmentRepository experienceRepository;

    public GetAttachmentByIdQuery(AttachmentRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public Attachment run(UUID id) {
        return ofNullable(experienceRepository.findOne(id))
                .orElseThrow(() -> new AttachmentNotFoundException(id));
    }
}
