package inno.edu.api.domain.appointment.commands.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculateAppointmentFeeRequest {
    public UUID mentorProfileId;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
}
