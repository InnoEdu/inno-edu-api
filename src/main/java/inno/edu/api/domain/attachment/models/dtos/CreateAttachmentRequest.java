package inno.edu.api.domain.attachment.models.dtos;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
public class CreateAttachmentRequest {
    private MultipartFile file;
    private String description;
}
