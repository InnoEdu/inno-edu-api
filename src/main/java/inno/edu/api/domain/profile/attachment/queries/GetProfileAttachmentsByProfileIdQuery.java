package inno.edu.api.domain.profile.attachment.queries;

import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Query
public class GetProfileAttachmentsByProfileIdQuery {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final ProfileAttachmentRepository profileAttachmentRepository;

    public GetProfileAttachmentsByProfileIdQuery(ProfileExistsAssertion profileExistsAssertion, ProfileAttachmentRepository profileAttachmentRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.profileAttachmentRepository = profileAttachmentRepository;
    }

    public List<Attachment> run(UUID profileId) {
        profileExistsAssertion.run(profileId);

        return profileAttachmentRepository
                .findByProfileId(profileId)
                .stream()
                .map(ProfileAttachment::getAttachment)
                .collect(toList());
    }
}
