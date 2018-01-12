package inno.edu.api.domain.profile.attachment.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.UUID;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProfileAttachmentPrimaryKey.class)
public class ProfileAttachment {
    @Id
    private UUID profileId;

    @Id
    private UUID attachmentId;
}
