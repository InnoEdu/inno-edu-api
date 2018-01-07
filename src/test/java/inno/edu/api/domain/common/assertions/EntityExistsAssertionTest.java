package inno.edu.api.domain.common.assertions;

import inno.edu.api.domain.appointment.exceptions.FeedbackNotFoundException;
import inno.edu.api.domain.appointment.repositories.FeedbackRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;
import java.util.function.Function;

import static inno.edu.api.support.AppointmentFactory.feedback;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EntityExistsAssertionTest {
    @Mock
    private FeedbackRepository feedbackRepository;

    private EntityExistsAssertion<FeedbackRepository, FeedbackNotFoundException, Function<UUID, FeedbackNotFoundException>> feedbackExistsAssertion;

    @Before
    public void setUp() {
        feedbackExistsAssertion = new EntityExistsAssertion<>(feedbackRepository, FeedbackNotFoundException::new);
    }

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