package inno.edu.api.domain.profile.attachment.commands.dtos;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Value
@Builder
public class UploadProfileAttachmentRequest {
    private UUID profileId;
    private MultipartFile file;
    private String description;
}
