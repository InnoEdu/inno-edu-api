package inno.edu.api.domain.appointment.root.commands;

import inno.edu.api.domain.appointment.root.assertions.FeedbackExistsAssertion;
import inno.edu.api.domain.appointment.root.repositories.FeedbackRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AppointmentFactory.feedback;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteFeedbackCommandTest {
    @Mock
    private FeedbackExistsAssertion feedbackExistsAssertion;

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private DeleteFeedbackCommand deleteFeedbackCommand;

    @Test
    public void shouldCallRepositoryToDeleteFeedback() {
        deleteFeedbackCommand.run(feedback().getId());

        verify(feedbackRepository).delete(feedback().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteFeedbackCommand.run(feedback().getId());

        verify(feedbackExistsAssertion).run(feedback().getId());
    }
}