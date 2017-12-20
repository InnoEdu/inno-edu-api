package inno.edu.api.domain.appointment.assertions;

import inno.edu.api.domain.appointment.exceptions.FeedbackNotFoundException;
import inno.edu.api.domain.appointment.repositories.FeedbackRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

@Assertion
public class FeedbackExistsAssertion {
    private final FeedbackRepository feedbackRepository;

    public FeedbackExistsAssertion(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public void run(UUID id) {
        if (!feedbackRepository.exists(id)) {
                throw new FeedbackNotFoundException(id);
        }
    }
}
