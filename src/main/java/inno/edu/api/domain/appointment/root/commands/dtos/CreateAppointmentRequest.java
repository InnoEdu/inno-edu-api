package inno.edu.api.domain.appointment.root.commands.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentRequest {
    @NotNull
    private UUID mentorProfileId;

    @NotNull
    private UUID menteeProfileId;

    @NotNull
    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime fromDateTime;

    @NotNull
    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime toDateTime;

    @NotNull
    private String description;
}
