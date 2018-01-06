package inno.edu.api.domain.profile.accomplishment.models;

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
public class Accomplishment {
    @Id
    private UUID id;
    private UUID profileId;

    private String title;
    private String description;
    private AccomplishmentType type;
}
