package inno.edu.api.controllers.appointment;

import inno.edu.api.controllers.appointment.resources.FeedbackResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.appointment.commands.CreateFeedbackCommand;
import inno.edu.api.domain.appointment.commands.DeleteFeedbackCommand;
import inno.edu.api.domain.appointment.models.Feedback;
import inno.edu.api.domain.appointment.queries.GetFeedbackByIdQuery;
import inno.edu.api.domain.appointment.queries.GetFeedbacksByAppointmentByIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.createFeedbackRequest;
import static inno.edu.api.support.AppointmentFactory.feedback;
import static inno.edu.api.support.AppointmentFactory.feedbacks;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private CreateFeedbackCommand createFeedbackCommand;

    @Mock
    private GetFeedbacksByAppointmentByIdQuery getFeedbacksByAppointmentByIdQuery;

    @Mock
    private DeleteFeedbackCommand deleteFeedbackCommand;

    @Mock
    private GetFeedbackByIdQuery getFeedbackByIdQuery;

    @InjectMocks
    private FeedbackController feedbackController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldCreateFeedback() {
        when(createFeedbackCommand.run(appointment().getId(), createFeedbackRequest())).thenReturn(feedback());

        ResponseEntity<Feedback> entity = feedbackController.postFeedback(appointment().getId(), createFeedbackRequest());

        assertThat(entity.getBody(), is(feedback()));
    }

    @Test
    public void shouldListFeedbacks() {
        when(getFeedbacksByAppointmentByIdQuery.run(appointment().getId()))
                .thenReturn(feedbacks());

        feedbackController.allFeedbacks(appointment().getId());

        verify(resourceBuilder).wrappedFrom(eq(feedbacks()), any(), eq(FeedbackResource.class));
    }

    @Test
    public void shouldDeleteFeedback() {
        feedbackController.deleteFeedback(feedback().getId());

        verify(deleteFeedbackCommand).run(feedback().getId());
    }

    @Test
    public void shouldGetFeedbackById() {
        when(getFeedbackByIdQuery.run(eq(feedback().getId()))).thenReturn(feedback());

        FeedbackResource feedbackResource = feedbackController.getFeedback(feedback().getId());

        assertThat(feedbackResource.getFeedback(), is(feedback()));
    }
}