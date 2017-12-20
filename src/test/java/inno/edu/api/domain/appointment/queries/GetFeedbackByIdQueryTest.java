package inno.edu.api.domain.appointment.queries;

import inno.edu.api.domain.appointment.exceptions.FeedbackNotFoundException;
import inno.edu.api.domain.appointment.models.Feedback;
import inno.edu.api.domain.appointment.repositories.FeedbackRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AppointmentFactory.feedback;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetFeedbackByIdQueryTest {
    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private GetFeedbackByIdQuery getFeedbackByIdQuery;

    @Test(expected = FeedbackNotFoundException.class)
    public void shouldThrowExceptionIfFeedbackDoesNotExist() {
        when(feedbackRepository.findOne(feedback().getId())).thenReturn(null);

        getFeedbackByIdQuery.run(feedback().getId());
    }

    @Test
    public void shouldReturnFeedback() {
        when(feedbackRepository.findOne(feedback().getId())).thenReturn(feedback());

        Feedback feedback = getFeedbackByIdQuery.run(feedback().getId());

        assertThat(feedback, is(feedback()));
    }
}