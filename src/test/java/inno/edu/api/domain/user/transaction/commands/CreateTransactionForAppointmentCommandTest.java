package inno.edu.api.domain.user.transaction.commands;

import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.profile.root.queries.GetProfileByIdQuery;
import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.models.TransactionType;
import inno.edu.api.domain.user.transaction.models.dtos.CreateTransactionRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.ACCEPTED;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.COMPLETED;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.DECLINED;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.STARTED;
import static inno.edu.api.domain.user.transaction.models.TransactionType.CREDIT;
import static inno.edu.api.domain.user.transaction.models.TransactionType.DEBIT;
import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateTransactionForAppointmentCommandTest {
    @Mock
    private GetAppointmentByIdQuery getAppointmentByIdQuery;

    @Mock
    private CreateTransactionCommand createTransactionCommand;

    @Mock
    private GetProfileByIdQuery getProfileByIdQuery;

    @InjectMocks
    private CreateTransactionForAppointmentCommand createTransactionForAppointmentCommand;

    @Before
    public void setUp() {
        when(getProfileByIdQuery.run(appointment().getMentorProfileId())).thenReturn(feiProfile());
        when(getProfileByIdQuery.run(appointment().getMenteeProfileId())).thenReturn(alanProfile());
    }

    @Test
    public void shouldDebitAppointmentFromMentee() {
        when(getAppointmentByIdQuery.run(appointment().getId())).thenReturn(appointment());

        createTransactionForAppointmentCommand.run(appointment().getId());

        CreateTransactionRequest expectedRequest = createTransactionRequest(DEBIT);

        verify(createTransactionCommand).run(appointment().getMenteeProfile().getUserId(), expectedRequest);
    }

    @Test
    public void shouldCreditAppointmentToMentee() {
        Appointment appointment = appointment().toBuilder().status(DECLINED).build();
        when(getAppointmentByIdQuery.run(appointment.getId())).thenReturn(appointment);

        createTransactionForAppointmentCommand.run(appointment.getId());

        CreateTransactionRequest expectedRequest = createTransactionRequest(CREDIT);

        verify(createTransactionCommand).run(appointment().getMenteeProfile().getUserId(), expectedRequest);
    }

    @Test
    public void shouldCreditAppointmentToMentor() {
        Appointment appointment = appointment().toBuilder().status(COMPLETED).build();
        when(getAppointmentByIdQuery.run(appointment.getId())).thenReturn(appointment);

        createTransactionForAppointmentCommand.run(appointment.getId());

        CreateTransactionRequest expectedRequest = createTransactionRequest(CREDIT);

        verify(createTransactionCommand).run(appointment().getMentorProfile().getUserId(), expectedRequest);
    }

    @Test
    public void shouldIgnoreAppointmentWithStartedStatus() {
        Appointment appointment = appointment().toBuilder().status(STARTED).build();
        when(getAppointmentByIdQuery.run(appointment.getId())).thenReturn(appointment);

        Transaction transaction = createTransactionForAppointmentCommand.run(appointment.getId());

        assertThat(transaction, is(nullValue()));
    }

    @Test
    public void shouldIgnoreAppointmentWithAcceptedStatus() {
        Appointment appointment = appointment().toBuilder().status(ACCEPTED).build();
        when(getAppointmentByIdQuery.run(appointment.getId())).thenReturn(appointment);

        Transaction transaction = createTransactionForAppointmentCommand.run(appointment.getId());

        assertThat(transaction, is(nullValue()));
    }

    private CreateTransactionRequest createTransactionRequest(TransactionType type) {
        return CreateTransactionRequest.builder()
                .appointmentId(appointment().getId())
                .type(type)
                .value(appointment().getFee())
                .build();
    }
}