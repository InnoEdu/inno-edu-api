package inno.edu.api.domain.availability.commands.dtos;

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
public class CreateAvailabilityRequest {
    private UUID mentorProfileId;

    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
}
