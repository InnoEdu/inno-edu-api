package inno.edu.api.domain.profile.experience.models.dtos;

import inno.edu.api.domain.profile.experience.models.ExperienceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateExperienceRequest {
    @NotNull
    @Size(max = 100)
    private String title;

    @NotNull
    @Size(max = 100)
    private String institution;

    @NotNull
    @Size(max = 255)
    private String location;

    @Size(max = 100)
    private String area;

    @DateTimeFormat(iso = DATE)
    private LocalDate fromDate;

    @DateTimeFormat(iso = DATE)
    private LocalDate toDate;

    private String description;

    @NotNull
    private ExperienceType type;
}
