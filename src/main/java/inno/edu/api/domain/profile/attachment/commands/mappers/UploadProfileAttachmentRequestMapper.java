package inno.edu.api.domain.profile.attachment.commands.mappers;

import inno.edu.api.domain.profile.attachment.commands.dtos.UploadProfileAttachmentRequest;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import org.mapstruct.Mapper;

@Mapper
public interface UploadProfileAttachmentRequestMapper {
    ProfileAttachment toProfileAttachment(UploadProfileAttachmentRequest uploadProfileAttachmentRequest, String link);
}
