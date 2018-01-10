package inno.edu.api.domain.profile.attachment.repositories;

import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProfileAttachmentRepository extends CrudRepository<ProfileAttachment, UUID> {
}
