package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteAppointmentCommand {
    private final AppointmentExistsAssertion appointmentExistsAssertion;
    private final AppointmentRepository appointmentRepository;

    public DeleteAppointmentCommand(AppointmentExistsAssertion appointmentExistsAssertion, AppointmentRepository appointmentRepository) {
        this.appointmentExistsAssertion = appointmentExistsAssertion;
        this.appointmentRepository = appointmentRepository;
    }

    public void run(UUID id) {
        appointmentExistsAssertion.run(id);
        appointmentRepository.delete(id);
    }
}
