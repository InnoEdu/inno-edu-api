package inno.edu.api.controllers.appointment;

import inno.edu.api.controllers.appointment.AppointmentController;
import inno.edu.api.controllers.appointment.resources.AppointmentResource;
import inno.edu.api.controllers.appointment.resources.FeedbackResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.appointment.commands.CreateAppointmentCommand;
import inno.edu.api.domain.appointment.commands.CreateFeedbackCommand;
import inno.edu.api.domain.appointment.commands.DeleteAppointmentCommand;
import inno.edu.api.domain.appointment.commands.DeleteFeedbackCommand;
import inno.edu.api.domain.appointment.commands.UpdateAppointmentCommand;
import inno.edu.api.domain.appointment.commands.UpdateAppointmentStatusCommand;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.Feedback;
import inno.edu.api.domain.appointment.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.queries.GetAppointmentsByMenteeProfileIdQuery;
import inno.edu.api.domain.appointment.queries.GetAppointmentsByMentorProfileIdQuery;
import inno.edu.api.domain.appointment.queries.GetAppointmentsQuery;
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

import static inno.edu.api.domain.appointment.models.AppointmentStatus.ACCEPTED;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.CANCELED;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.DECLINED;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.appointments;
import static inno.edu.api.support.AppointmentFactory.createAppointmentRequest;
import static inno.edu.api.support.AppointmentFactory.createFeedbackRequest;
import static inno.edu.api.support.AppointmentFactory.emptyReason;
import static inno.edu.api.support.AppointmentFactory.feedback;
import static inno.edu.api.support.AppointmentFactory.feedbacks;
import static inno.edu.api.support.AppointmentFactory.proposedAppointments;
import static inno.edu.api.support.AppointmentFactory.reason;
import static inno.edu.api.support.AppointmentFactory.updateAppointmentRequest;
import static inno.edu.api.support.AppointmentFactory.updatedAppointment;
import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.UserFactory.fei;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private GetAppointmentsQuery getAppointmentsQuery;

    @Mock
    private CreateAppointmentCommand createAppointmentCommand;

    @Mock
    private UpdateAppointmentCommand updateAppointmentCommand;

    @Mock
    private DeleteAppointmentCommand deleteAppointmentCommand;

    @Mock
    private GetAppointmentByIdQuery getAppointmentByIdQuery;

    @Mock
    private GetAppointmentsByMentorProfileIdQuery getAppointmentsByMentorProfileIdQuery;

    @Mock
    private GetAppointmentsByMenteeProfileIdQuery getAppointmentsByMenteeProfileIdQuery;

    @Mock
    private UpdateAppointmentStatusCommand updateAppointmentStatusCommand;

    @Mock
    private CreateFeedbackCommand createFeedbackCommand;

    @Mock
    private GetFeedbacksByAppointmentByIdQuery getFeedbacksByAppointmentByIdQuery;

    @Mock
    private DeleteFeedbackCommand deleteFeedbackCommand;

    @Mock
    private GetFeedbackByIdQuery getFeedbackByIdQuery;

    @InjectMocks
    private AppointmentController appointmentController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetAppointmentById() {
        when(getAppointmentByIdQuery.run(eq(appointment().getId()))).thenReturn(appointment());

        AppointmentResource appointmentResource = appointmentController.get(appointment().getId());

        assertThat(appointmentResource.getAppointment(), is(appointment()));
    }

    @Test
    public void shouldListAppointments() {
        when(getAppointmentsQuery.run()).thenReturn(appointments());

        appointmentController.all();

        verify(resourceBuilder).wrappedFrom(eq(appointments()), any(), eq(AppointmentResource.class));
    }

    @Test
    public void shouldListAppointmentsByMentor() {
        when(getAppointmentsByMentorProfileIdQuery.run(fei().getId(), null)).thenReturn(appointments());

        appointmentController.allByMentor(fei().getId(), null);

        verify(resourceBuilder).wrappedFrom(eq(appointments()), any(), eq(AppointmentResource.class));
    }

    @Test
    public void shouldListAppointmentsByMentorAndStatus() {
        when(getAppointmentsByMentorProfileIdQuery.run(fei().getId(), PROPOSED)).thenReturn(proposedAppointments());

        appointmentController.allByMentor(fei().getId(), PROPOSED);

        verify(resourceBuilder).wrappedFrom(eq(proposedAppointments()), any(), eq(AppointmentResource.class));
    }

    @Test
    public void shouldListAppointmentsByMentee() {
        when(getAppointmentsByMenteeProfileIdQuery.run(alan().getId(), null)).thenReturn(appointments());

        appointmentController.allByMentee(alan().getId(), null);

        verify(resourceBuilder).wrappedFrom(eq(appointments()), any(), eq(AppointmentResource.class));
    }

    @Test
    public void shouldListAppointmentsByMenteeAndStatus() {
        when(getAppointmentsByMenteeProfileIdQuery.run(alan().getId(), PROPOSED)).thenReturn(proposedAppointments());

        appointmentController.allByMentee(alan().getId(), PROPOSED);

        verify(resourceBuilder).wrappedFrom(eq(proposedAppointments()), any(), eq(AppointmentResource.class));
    }

    @Test
    public void shouldCreateNewAppointment() {
        when(createAppointmentCommand.run(createAppointmentRequest())).thenReturn(appointment());

        ResponseEntity<Appointment> entity = appointmentController.post(createAppointmentRequest());

        assertThat(entity.getBody(), is(appointment()));
    }

    @Test
    public void shouldUpdateAppointment() {
        when(updateAppointmentCommand.run(appointment().getId(), updateAppointmentRequest())).thenReturn(updatedAppointment());

        ResponseEntity<Appointment> entity = appointmentController.put(appointment().getId(), updateAppointmentRequest());

        assertThat(entity.getBody(), is(updatedAppointment()));
    }

    @Test
    public void shouldDeleteAppointment() {
        appointmentController.delete(appointment().getId());

        verify(deleteAppointmentCommand).run(appointment().getId());
    }

    @Test
    public void shouldCancelAppointment() {
        appointmentController.cancel(appointment().getId(), reason());

        verify(updateAppointmentStatusCommand).run(appointment().getId(), reason(), CANCELED);
    }

    @Test
    public void shouldDeclineAppointment() {
        appointmentController.decline(appointment().getId(), reason());

        verify(updateAppointmentStatusCommand).run(appointment().getId(), reason(), DECLINED);
    }

    @Test
    public void shouldAcceptAppointment() {
        appointmentController.accept(appointment().getId());

        verify(updateAppointmentStatusCommand).run(appointment().getId(), emptyReason(), ACCEPTED);
    }

    @Test
    public void shouldCreateFeedback() {
        when(createFeedbackCommand.run(appointment().getId(), createFeedbackRequest())).thenReturn(feedback());

        ResponseEntity<Feedback> entity = appointmentController.postFeedback(appointment().getId(), createFeedbackRequest());

        assertThat(entity.getBody(), is(feedback()));
    }

    @Test
    public void shouldListFeedbacks() {
        when(getFeedbacksByAppointmentByIdQuery.run(appointment().getId()))
                .thenReturn(feedbacks());

        appointmentController.allFeedbacks(appointment().getId());

        verify(resourceBuilder).wrappedFrom(eq(feedbacks()), any(), eq(FeedbackResource.class));
    }

    @Test
    public void shouldDeleteFeedback() {
        appointmentController.deleteFeedback(feedback().getId());

        verify(deleteFeedbackCommand).run(feedback().getId());
    }

    @Test
    public void shouldGetFeedbackById() {
        when(getFeedbackByIdQuery.run(eq(feedback().getId()))).thenReturn(feedback());

        FeedbackResource feedbackResource = appointmentController.getFeedback(feedback().getId());

        assertThat(feedbackResource.getFeedback(), is(feedback()));
    }
}