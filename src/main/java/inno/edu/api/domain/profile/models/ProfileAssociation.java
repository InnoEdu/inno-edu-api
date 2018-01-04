package inno.edu.api.domain.profile.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProfileAssociation {
    @Id
    private UUID id;

    private UUID profileId;
    private UUID schoolId;

    private RequestStatus status;
    private String description;
}
