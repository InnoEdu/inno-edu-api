package inno.edu.api.domain.profile.commands.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateMentorProfileRequest {
    @NotNull
    private UUID mentorId;

    @NotNull
    private UUID schoolId;

    @NotNull
    @Email
    @Size(max = 255)
    private String email;

    @NotNull
    private String description;
}
