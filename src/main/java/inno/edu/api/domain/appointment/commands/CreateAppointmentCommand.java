package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.commands.dtos.CreateAppointmentRequest;
import inno.edu.api.domain.appointment.commands.mappers.CreateAppointmentRequestMapper;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.profile.assertions.MenteeProfileExistsAssertion;
import inno.edu.api.domain.profile.assertions.MentorProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static java.util.UUID.randomUUID;

@Command
public class CreateAppointmentCommand {
    private final CreateAppointmentRequestMapper createAppointmentRequestMapper;

    private final AppointmentRepository appointmentRepository;

    private final MenteeProfileExistsAssertion menteeProfileExistsAssertion;
    private final MentorProfileExistsAssertion mentorProfileExistsAssertion;

    public CreateAppointmentCommand(CreateAppointmentRequestMapper createAppointmentRequestMapper, AppointmentRepository appointmentRepository, MenteeProfileExistsAssertion menteeProfileExistsAssertion, MentorProfileExistsAssertion mentorProfileExistsAssertion) {
        this.createAppointmentRequestMapper = createAppointmentRequestMapper;
        this.appointmentRepository = appointmentRepository;
        this.menteeProfileExistsAssertion = menteeProfileExistsAssertion;
        this.mentorProfileExistsAssertion = mentorProfileExistsAssertion;
    }

    public Appointment run(CreateAppointmentRequest createAppointmentRequest) {
        menteeProfileExistsAssertion.run(createAppointmentRequest.getMenteeProfileId());
        mentorProfileExistsAssertion.run(createAppointmentRequest.getMentorProfileId());

        Appointment appointment = createAppointmentRequestMapper.toAppointment(createAppointmentRequest);

        appointment.setId(randomUUID());
        appointment.setStatus(PROPOSED);

        return appointmentRepository.save(appointment);
    }
}
