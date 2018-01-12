package inno.edu.api.domain.school.attachment.repositories;

import inno.edu.api.domain.school.attachment.models.SchoolAttachment;
import inno.edu.api.domain.school.attachment.models.SchoolAttachmentPrimaryKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface SchoolAttachmentRepository extends CrudRepository<SchoolAttachment, SchoolAttachmentPrimaryKey> {
    List<SchoolAttachment> findBySchoolId(UUID schoolId);
}
