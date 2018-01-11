package inno.edu.api.domain.profile.attachment.queries;

import inno.edu.api.domain.profile.attachment.exceptions.ProfileAttachmentNotFoundException;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetProfileAttachmentByIdQuery {
    private final ProfileAttachmentRepository experienceRepository;

    public GetProfileAttachmentByIdQuery(ProfileAttachmentRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public ProfileAttachment run(UUID id) {
        return ofNullable(experienceRepository.findOne(id))
                .orElseThrow(() -> new ProfileAttachmentNotFoundException(id));
    }
}
