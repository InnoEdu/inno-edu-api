package inno.edu.api.domain.profile.attachment.commands.mappers;

import inno.edu.api.domain.profile.attachment.commands.dtos.CreateProfileAttachmentRequest;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import org.mapstruct.Mapper;

@Mapper
public interface CreateProfileAttachmentRequestMapper {
    ProfileAttachment toProfileAttachment(CreateProfileAttachmentRequest createProfileAttachmentRequest, String url);
}
