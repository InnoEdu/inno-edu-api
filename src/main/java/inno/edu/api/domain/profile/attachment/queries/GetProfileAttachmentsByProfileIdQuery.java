package inno.edu.api.domain.profile.attachment.queries;

import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

@Query
public class GetProfileAttachmentsByProfileIdQuery {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final ProfileAttachmentRepository attachmentRepository;

    public GetProfileAttachmentsByProfileIdQuery(ProfileExistsAssertion profileExistsAssertion, ProfileAttachmentRepository attachmentRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.attachmentRepository = attachmentRepository;
    }

    public List<ProfileAttachment> run(UUID profileId) {
        profileExistsAssertion.run(profileId);
        return attachmentRepository.findByProfileId(profileId);
    }
}
