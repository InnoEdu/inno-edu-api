package inno.edu.api.domain.profile.root.models.dtos;

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
public class UpdateProfileRequest {
    @NotNull
    private String description;
    @NotNull
    private String location;

    private UUID profileReferenceId;
}
