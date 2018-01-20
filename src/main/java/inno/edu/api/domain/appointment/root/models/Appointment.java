package inno.edu.api.domain.appointment.root.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import inno.edu.api.domain.appointment.feedback.models.Feedback;
import inno.edu.api.domain.profile.root.models.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    private UUID id;

    private UUID mentorProfileId;
    private UUID menteeProfileId;

    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    private String description;
    private BigDecimal fee;

    @Enumerated
    private AppointmentStatus status;

    private String reason;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "mentorProfileId", updatable = false, insertable = false)
    @JsonIgnore
    private Profile mentorProfile;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "menteeProfileId", updatable = false, insertable = false)
    @JsonIgnore
    private Profile menteeProfile;

    @OneToMany(fetch = LAZY)
    @JoinColumn(name = "appointmentId", updatable = false, insertable = false)
    @JsonIgnore
    private List<Feedback> feedbacks;
}
