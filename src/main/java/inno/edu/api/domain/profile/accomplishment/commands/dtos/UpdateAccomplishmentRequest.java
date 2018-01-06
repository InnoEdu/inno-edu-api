package inno.edu.api.domain.profile.accomplishment.commands.dtos;

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
public class UpdateAccomplishmentRequest {
    @NotNull
    @Size(max = 100)
    private String title;

    @NotNull
    private String description;
}
