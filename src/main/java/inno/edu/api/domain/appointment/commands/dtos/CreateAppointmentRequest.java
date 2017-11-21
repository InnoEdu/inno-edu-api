package inno.edu.api.domain.appointment.commands.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentRequest {
    private UUID mentorProfileId;
    private UUID menteeProfileId;

    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    private String description;
}
