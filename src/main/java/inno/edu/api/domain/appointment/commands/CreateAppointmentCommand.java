package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.university.exceptions.UniversityNotFoundException;
import inno.edu.api.domain.university.repositories.UniversityRepository;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.springframework.stereotype.Component;

import static java.util.UUID.randomUUID;

@Component
public class CreateAppointmentCommand {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;

    public CreateAppointmentCommand(AppointmentRepository appointmentRepository, UserRepository userRepository, UniversityRepository universityRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.universityRepository = universityRepository;
    }

    public Appointment run(Appointment appointment) {
        if (!userRepository.exists(appointment.getMentorId())) {
            throw new UserNotFoundException(appointment.getMentorId());
        }
        if (!userRepository.exists(appointment.getMenteeId())) {
            throw new UserNotFoundException(appointment.getMenteeId());
        }
        if (!universityRepository.exists(appointment.getUniversityId())) {
            throw new UniversityNotFoundException(appointment.getUniversityId());
        }

        appointment.setId(randomUUID());
        return appointmentRepository.save(appointment);
    }
}
