package inno.edu.api.domain.appointment.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    private UUID id;

    private UUID mentorId;
    private UUID menteeId;
    private UUID universityId;

    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    @Enumerated
    private AppointmentStatus status;

}
