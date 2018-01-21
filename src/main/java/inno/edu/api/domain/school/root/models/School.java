package inno.edu.api.domain.school.root.models;

import inno.edu.api.domain.attachment.models.Attachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class School {
    @Id
    private UUID id;
    private UUID photoId;

    private String name;
    private String description;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "photoId", updatable = false, insertable = false)
    private Attachment photo;
}
