package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.profile.assertions.MenteeProfileExistsAssertion;
import inno.edu.api.domain.profile.assertions.MentorProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static java.util.UUID.randomUUID;

@Command
public class CreateAppointmentCommand {
    private final AppointmentRepository appointmentRepository;

    private final MenteeProfileExistsAssertion menteeProfileExistsAssertion;
    private final MentorProfileExistsAssertion mentorProfileExistsAssertion;

    public CreateAppointmentCommand(AppointmentRepository appointmentRepository, MenteeProfileExistsAssertion menteeProfileExistsAssertion, MentorProfileExistsAssertion mentorProfileExistsAssertion) {
        this.appointmentRepository = appointmentRepository;
        this.menteeProfileExistsAssertion = menteeProfileExistsAssertion;
        this.mentorProfileExistsAssertion = mentorProfileExistsAssertion;
    }

    public Appointment run(Appointment appointment) {
        menteeProfileExistsAssertion.run(appointment.getMenteeProfileId());
        mentorProfileExistsAssertion.run(appointment.getMentorProfileId());

        appointment.setId(randomUUID());
        appointment.setStatus(PROPOSED);

        return appointmentRepository.save(appointment);
    }
}
