package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.user.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.user.repositories.MenteeProfileRepository;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static java.util.UUID.randomUUID;

@Command
public class CreateAppointmentCommand {
    private final AppointmentRepository appointmentRepository;
    private final MenteeProfileRepository menteeProfileRepository;
    private final MentorProfileRepository mentorProfileRepository;

    public CreateAppointmentCommand(AppointmentRepository appointmentRepository, MenteeProfileRepository menteeProfileRepository, MentorProfileRepository mentorProfileRepository) {
        this.appointmentRepository = appointmentRepository;
        this.menteeProfileRepository = menteeProfileRepository;
        this.mentorProfileRepository = mentorProfileRepository;
    }

    public Appointment run(Appointment appointment) {
        if (!mentorProfileRepository.exists(appointment.getMentorProfileId())) {
            throw new ProfileNotFoundException(appointment.getMentorProfileId());
        }
        if (!menteeProfileRepository.exists(appointment.getMenteeProfileId())) {
            throw new ProfileNotFoundException(appointment.getMenteeProfileId());
        }
        appointment.setId(randomUUID());
        appointment.setStatus(PROPOSED);

        return appointmentRepository.save(appointment);
    }
}
