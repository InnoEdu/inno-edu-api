package inno.edu.api.domain.user.transaction.commands;

import inno.edu.api.domain.appointment.root.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.user.transaction.models.TransactionType;
import inno.edu.api.domain.user.transaction.models.dtos.CreateTransactionRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.COMPLETED;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.DECLINED;
import static inno.edu.api.domain.user.transaction.models.TransactionType.CREDIT;
import static inno.edu.api.domain.user.transaction.models.TransactionType.DEBIT;
import static inno.edu.api.support.AppointmentFactory.appointment;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateTransactionForAppointmentCommandTest {
    @Mock
    private AppointmentExistsAssertion appointmentExistsAssertion;

    @Mock
    private CreateTransactionCommand createTransactionCommand;

    @InjectMocks
    private CreateTransactionForAppointmentCommand createTransactionForAppointmentCommand;

    @Test
    public void shouldDebitAppointmentFromMentee() {
        createTransactionForAppointmentCommand.run(appointment());

        CreateTransactionRequest expectedRequest = createTransactionRequest(DEBIT);

        verify(createTransactionCommand).run(appointment().getMenteeProfile().getUserId(), expectedRequest);
    }

    @Test
    public void shouldCreditAppointmentToMentee() {
        Appointment appointment = appointment().toBuilder().status(DECLINED).build();

        createTransactionForAppointmentCommand.run(appointment);

        CreateTransactionRequest expectedRequest = createTransactionRequest(CREDIT);

        verify(createTransactionCommand).run(appointment().getMenteeProfile().getUserId(), expectedRequest);
    }

    @Test
    public void shouldCreditAppointmentToMentor() {
        Appointment appointment = appointment().toBuilder().status(COMPLETED).build();

        createTransactionForAppointmentCommand.run(appointment);

        CreateTransactionRequest expectedRequest = createTransactionRequest(CREDIT);

        verify(createTransactionCommand).run(appointment().getMentorProfile().getUserId(), expectedRequest);
    }

    @Test
    public void shouldRunAllAssertions() {
        createTransactionForAppointmentCommand.run(appointment());

        verify(appointmentExistsAssertion).run(appointment().getId());
    }
    private CreateTransactionRequest createTransactionRequest(TransactionType type) {
        return CreateTransactionRequest.builder()
                .appointmentId(appointment().getId())
                .type(type)
                .value(appointment().getFee())
                .build();
    }
}