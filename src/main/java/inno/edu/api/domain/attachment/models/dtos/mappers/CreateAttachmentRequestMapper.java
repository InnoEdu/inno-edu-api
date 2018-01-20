package inno.edu.api.domain.attachment.models.dtos.mappers;

import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.Attachment;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAttachmentRequestMapper {
    Attachment toAttachment(CreateAttachmentRequest createAttachmentRequest, String url);
}
