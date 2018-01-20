package inno.edu.api.domain.profile.accomplishment.models.dtos;

import inno.edu.api.domain.profile.accomplishment.models.AccomplishmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccomplishmentRequest {
    @NotNull
    @Size(max = 100)
    private String title;

    @NotNull
    private String description;

    @NotNull
    private AccomplishmentType type;
}
