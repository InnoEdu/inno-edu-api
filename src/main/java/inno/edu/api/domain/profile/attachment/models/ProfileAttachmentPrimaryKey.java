package inno.edu.api.domain.profile.attachment.models;

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
public class ProfileAttachmentPrimaryKey implements Serializable {
    private UUID profileId;
    private UUID attachmentId;
}
