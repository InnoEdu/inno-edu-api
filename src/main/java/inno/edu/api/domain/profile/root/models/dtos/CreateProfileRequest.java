package inno.edu.api.domain.profile.root.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfileRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private String description;

    @NotNull
    @Size(max = 100)
    private String location;

    @Size(max = 100)
    private String company;

    private UUID profileReferenceId;
}
