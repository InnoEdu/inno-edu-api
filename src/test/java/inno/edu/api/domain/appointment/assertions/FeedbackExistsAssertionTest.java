package inno.edu.api.domain.appointment.assertions;

import inno.edu.api.domain.appointment.exceptions.FeedbackNotFoundException;
import inno.edu.api.domain.appointment.repositories.FeedbackRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AppointmentFactory.feedback;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackExistsAssertionTest {
    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackExistsAssertion feedbackExistsAssertion;

    @Test(expected = FeedbackNotFoundException.class)
    public void shouldThrowExceptionIfFeedbackDoesNotExist() {
        when(feedbackRepository.exists(feedback().getId())).thenReturn(false);

        feedbackExistsAssertion.run(feedback().getId());
    }

    @Test
    public void shouldNotThrowExceptionIfFeedbackExists() {
        when(feedbackRepository.exists(feedback().getId())).thenReturn(true);

        feedbackExistsAssertion.run(feedback().getId());
    }
}