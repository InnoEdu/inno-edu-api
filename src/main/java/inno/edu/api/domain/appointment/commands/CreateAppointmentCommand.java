package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.user.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static java.util.UUID.randomUUID;

@Command
public class CreateAppointmentCommand {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final MentorProfileRepository mentorProfileRepository;

    public CreateAppointmentCommand(AppointmentRepository appointmentRepository, UserRepository userRepository, MentorProfileRepository mentorProfileRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.mentorProfileRepository = mentorProfileRepository;
    }

    public Appointment run(Appointment appointment) {
        if (!mentorProfileRepository.exists(appointment.getMentorProfileId())) {
            throw new ProfileNotFoundException(appointment.getMentorProfileId());
        }
        if (!userRepository.exists(appointment.getMenteeId())) {
            throw new UserNotFoundException(appointment.getMenteeId());
        }
        appointment.setId(randomUUID());
        appointment.setStatus(PROPOSED);

        return appointmentRepository.save(appointment);
    }
}
