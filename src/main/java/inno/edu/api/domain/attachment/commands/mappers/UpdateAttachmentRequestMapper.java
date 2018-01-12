package inno.edu.api.domain.attachment.commands.mappers;

import inno.edu.api.domain.attachment.commands.dtos.UpdateAttachmentRequest;
import inno.edu.api.domain.attachment.models.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateAttachmentRequestMapper {
    void setAttachment(UpdateAttachmentRequest updateAttachmentRequest, @MappingTarget Attachment attachment);
}
