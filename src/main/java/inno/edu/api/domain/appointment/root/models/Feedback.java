package inno.edu.api.domain.appointment.root.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    private UUID id;

    private UUID appointmentId;

    @Enumerated
    private FeedbackSource source;

    private int rating;
    private String description;
}
