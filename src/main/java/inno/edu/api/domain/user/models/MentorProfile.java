package inno.edu.api.domain.user.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorProfile {
    @Id
    private UUID id;

    private UUID mentorId;
    private UUID universityId;
    private String email;
    private ProfileStatus status;
}
