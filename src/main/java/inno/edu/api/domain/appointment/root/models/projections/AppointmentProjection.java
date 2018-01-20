package inno.edu.api.domain.appointment.root.models.projections;

import inno.edu.api.domain.appointment.root.models.AppointmentStatus;
import inno.edu.api.domain.appointment.root.models.resources.FeedbackResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentProjection {
    private UUID id;

    private UUID mentorProfileId;
    private UUID menteeProfileId;

    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    private String description;
    private BigDecimal fee;

    private AppointmentStatus status;

    private String reason;

    private String mentorFirstName;
    private String mentorLastName;

    private String menteeFirstName;
    private String menteeLastName;

    private String mentorPhotoUrl;
    private String menteePhotoUrl;

    private List<FeedbackResource> feedbacks;
}
