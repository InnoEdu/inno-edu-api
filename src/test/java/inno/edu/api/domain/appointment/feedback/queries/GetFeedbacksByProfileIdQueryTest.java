package inno.edu.api.domain.appointment.feedback.queries;

import inno.edu.api.domain.appointment.feedback.models.Feedback;
import inno.edu.api.domain.appointment.feedback.respositories.FeedbackRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.AppointmentFactory.feedbacks;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetFeedbacksByProfileIdQueryTest {
    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private GetFeedbacksByProfileIdQuery getFeedbacksByProfileIdQuery;

    @Test
    public void shouldGetFeedbacksByProfile() {
        when(feedbackRepository.findByProfileId(feiProfile().getId())).thenReturn(feedbacks());

        List<Feedback> feedbacks = getFeedbacksByProfileIdQuery.run(feiProfile().getId());

        assertThat(feedbacks, is(feedbacks()));
    }

    @Test
    public void shouldRunAllAssertions(){
        getFeedbacksByProfileIdQuery.run(feiProfile().getId());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }
}