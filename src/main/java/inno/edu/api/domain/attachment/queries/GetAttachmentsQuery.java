package inno.edu.api.domain.attachment.queries;

import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.repositories.AttachmentRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Query
public class GetAttachmentsQuery {
    private final AttachmentRepository attachmentRepository;

    public GetAttachmentsQuery(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public List<Attachment> run() {
        return newArrayList(attachmentRepository.findAll());
    }
}
