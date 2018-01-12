package inno.edu.api.domain.school.attachment.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SchoolAttachmentPrimaryKey implements Serializable {
    private UUID schoolId;
    private UUID attachmentId;
}
