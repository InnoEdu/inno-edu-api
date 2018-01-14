package inno.edu.api.domain.appointment.root.queries;

import inno.edu.api.domain.appointment.root.exceptions.FeedbackNotFoundException;
import inno.edu.api.domain.appointment.root.models.Feedback;
import inno.edu.api.domain.appointment.root.repositories.FeedbackRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetFeedbackByIdQuery {
    private final FeedbackRepository feedbackRepository;

    public GetFeedbackByIdQuery(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback run(UUID id) {
        return ofNullable(feedbackRepository.findOne(id))
                .orElseThrow(() -> new FeedbackNotFoundException(id));
    }
}
