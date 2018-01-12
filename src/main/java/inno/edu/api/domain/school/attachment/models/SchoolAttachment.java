package inno.edu.api.domain.school.attachment.models;

import inno.edu.api.domain.attachment.models.Attachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SchoolAttachmentPrimaryKey.class)
public class SchoolAttachment {
    @Id
    private UUID schoolId;

    @Id
    private UUID attachmentId;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "attachmentId", updatable = false, insertable = false)
    private Attachment attachment;
}
