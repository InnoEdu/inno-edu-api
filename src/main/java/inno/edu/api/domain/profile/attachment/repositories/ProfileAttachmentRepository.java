package inno.edu.api.domain.profile.attachment.repositories;

import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachmentPrimaryKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProfileAttachmentRepository extends CrudRepository<ProfileAttachment, ProfileAttachmentPrimaryKey> {
    List<ProfileAttachment> findByProfileId(UUID profileId);
}
