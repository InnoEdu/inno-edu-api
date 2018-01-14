package inno.edu.api.domain.appointment.feedback.commands.dtos;

import inno.edu.api.domain.appointment.feedback.models.FeedbackSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeedbackRequest {
    @Enumerated
    private FeedbackSource source;

    private int rating;
    private String description;
}
