package inno.edu.api.domain.attachment.repositories;

import inno.edu.api.domain.attachment.models.Attachment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AttachmentRepository extends CrudRepository<Attachment, UUID> {
}
