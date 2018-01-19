package inno.edu.api.domain.appointment.feedback.queries;

import inno.edu.api.domain.appointment.feedback.models.Feedback;
import inno.edu.api.domain.appointment.feedback.respositories.FeedbackRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

@Query
public class GetFeedbacksByProfileIdQuery {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final FeedbackRepository feedbackRepository;

    public GetFeedbacksByProfileIdQuery(ProfileExistsAssertion profileExistsAssertion, FeedbackRepository feedbackRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> run(UUID profileId) {
        profileExistsAssertion.run(profileId);

        return feedbackRepository.findByProfileId(profileId);
    }
}
