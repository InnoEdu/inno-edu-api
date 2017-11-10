package inno.edu.api.domain.appointment.repositories;

import inno.edu.api.domain.appointment.models.Appointment;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AppointmentRepository extends CrudRepository<Appointment, UUID> {
}
