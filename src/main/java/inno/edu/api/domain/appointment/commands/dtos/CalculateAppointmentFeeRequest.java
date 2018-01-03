package inno.edu.api.domain.appointment.commands.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CalculateAppointmentFeeRequest {
    private UUID mentorProfileId;

    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime fromDateTime;

    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime toDateTime;
}
