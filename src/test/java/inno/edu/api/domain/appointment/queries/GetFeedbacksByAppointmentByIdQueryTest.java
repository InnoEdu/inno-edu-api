package inno.edu.api.domain.appointment.queries;

import inno.edu.api.domain.appointment.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.models.Feedback;
import inno.edu.api.domain.appointment.repositories.FeedbackRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.feedback;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetFeedbacksByAppointmentByIdQueryTest {
    @Mock
    private AppointmentExistsAssertion appointmentExistsAssertion;

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private GetFeedbacksByAppointmentByIdQuery getFeedbacksByAppointmentByIdQuery;

    @Test
    public void shouldReturnFeedbacksForAppointment() {
        when(feedbackRepository.findByAppointmentId(appointment().getId()))
                .thenReturn(singletonList(feedback()));

        List<Feedback> feedbacks = getFeedbacksByAppointmentByIdQuery.run(appointment().getId());

        assertThat(feedbacks, is(singletonList(feedback())));
    }

    @Test
    public void shouldRunAllAssertions() {
        getFeedbacksByAppointmentByIdQuery.run(appointment().getId());

        verify(appointmentExistsAssertion).run(appointment().getId());
    }
}