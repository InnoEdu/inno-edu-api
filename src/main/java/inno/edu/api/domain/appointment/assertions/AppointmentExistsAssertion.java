package inno.edu.api.domain.appointment.assertions;

import inno.edu.api.domain.appointment.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

@Assertion
public class AppointmentExistsAssertion {
    private final AppointmentRepository appointmentRepository;

    public AppointmentExistsAssertion(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void run(UUID id) {
        if (!appointmentRepository.exists(id)) {
                throw new AppointmentNotFoundException(id);
        }
    }
}
