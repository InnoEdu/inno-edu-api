package inno.edu.api.domain.profile.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
    @Id
    private UUID id;
    private UUID profileId;

    private String title;
    private String institution;
    private String location;
    private String area;

    private LocalDate fromDate;
    private LocalDate toDate;

    private String description;
    private ExperienceType type;
}
