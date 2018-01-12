package inno.edu.api.domain.profile.attachment.repositories;

import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachmentPrimaryKey;
import org.springframework.data.repository.CrudRepository;

public interface ProfileAttachmentRepository extends CrudRepository<ProfileAttachment, ProfileAttachmentPrimaryKey> {
}
