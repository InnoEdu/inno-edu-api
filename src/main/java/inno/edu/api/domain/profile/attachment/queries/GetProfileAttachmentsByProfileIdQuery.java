package inno.edu.api.domain.profile.attachment.queries;

import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

@Query
public class GetProfileAttachmentsByProfileIdQuery {
    public List<Attachment> run(UUID profileId) {
        return null;
    }
}
