package inno.edu.api.domain.profile.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MentorProfile {
    @Id
    private UUID id;

    private UUID mentorId;
    private UUID schoolId;
    private String email;
    private String description;

    @Enumerated
    private ProfileStatus status;
}