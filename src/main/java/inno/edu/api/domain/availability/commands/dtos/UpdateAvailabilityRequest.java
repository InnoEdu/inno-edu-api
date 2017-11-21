package inno.edu.api.domain.availability.commands.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAvailabilityRequest {
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
}
