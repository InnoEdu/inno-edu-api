package inno.edu.api.domain.profile.queries.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorProfileUser {
    private UUID id;

    private UUID mentorId;
    private UUID schoolId;
    private String email;
    private String description;

    private String firstName;
    private String lastName;
}
