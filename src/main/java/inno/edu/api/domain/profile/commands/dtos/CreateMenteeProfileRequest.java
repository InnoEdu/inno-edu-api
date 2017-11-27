package inno.edu.api.domain.profile.commands.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenteeProfileRequest {
    @NotNull
    private UUID menteeId;

    @NotNull
    private String description;
}
