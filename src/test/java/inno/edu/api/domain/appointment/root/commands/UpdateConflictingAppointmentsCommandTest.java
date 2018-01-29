package inno.edu.api.domain.appointment.root.commands;

import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.models.AppointmentStatus;
import inno.edu.api.domain.appointment.root.queries.GetAppointmentsByMentorProfileIdQuery;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import inno.edu.api.domain.user.transaction.commands.CreateTransactionForAppointmentCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.UNAVAILABLE;
import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static java.util.UUID.fromString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateConflictingAppointmentsCommandTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private GetAppointmentsByMentorProfileIdQuery getAppointmentsByMentorProfileIdQuery;

    @Mock
    private CreateTransactionForAppointmentCommand createTransactionForAppointmentCommand;

    @InjectMocks
    private UpdateConflictingAppointmentsCommand updateConflictingAppointmentsCommand;

    @Before
    public void setUp() {
        when(getAppointmentsByMentorProfileIdQuery.run(feiProfile().getId(), PROPOSED))
                .thenReturn(appointmentList());

        when(appointmentRepository.save(anyCollectionOf(Appointment.class)))
                .thenAnswer(answer -> answer.getArguments()[0]);
    }

    @Test
    public void shouldReturnOtherProposedAppointments() {
        List<Appointment> appointments = updateConflictingAppointmentsCommand.run(appointment());
        assertThat(appointments, is(conflictedAppointments(UNAVAILABLE)));
    }

    @Test
    public void shouldCreateTransactionsForOtherProposedAppointments() {
        List<Appointment> appointments = updateConflictingAppointmentsCommand.run(appointment());

        appointments.forEach((appointment -> verify(createTransactionForAppointmentCommand).run(appointment.getId())));
    }

    private List<Appointment> conflictedAppointments(AppointmentStatus status) {
        Appointment conflict = appointment().toBuilder().id(fromString("2bf5cd3b-14d1-439b-b71e-3e95fe418b0f"))
                .status(status)
                .description("conflict")
                .build();

        Appointment conflictBefore = appointment().toBuilder().id(fromString("de484567-9329-4912-bc25-0aec370e8e3a"))
                .status(status)
                .fromDateTime(appointment().getFromDateTime().plusMinutes(10))
                .description("conflictBefore")
                .build();

        Appointment conflictAfter = appointment().toBuilder().id(fromString("d599b9a4-d39f-4d7a-bbd4-b6f4eb95e7e9"))
                .status(status)
                .toDateTime(appointment().getToDateTime().minusMinutes(10))
                .description("conflictAfter")
                .build();

        return newArrayList(conflict, conflictAfter, conflictBefore);
    }

    private List<Appointment> appointmentList() {
        Appointment noConflict = appointment().toBuilder()
                .id(fromString("1b9449ec-c292-47c5-8a51-750a4f810edf"))
                .description("No conflict")
                .fromDateTime(appointment().getFromDateTime().plusHours(1))
                .toDateTime(appointment().getToDateTime().plusHours(1))
                .build();

        List<Appointment> allAppointments = newArrayList(noConflict, appointment());
        allAppointments.addAll(conflictedAppointments(PROPOSED));
        return allAppointments;
    }


}