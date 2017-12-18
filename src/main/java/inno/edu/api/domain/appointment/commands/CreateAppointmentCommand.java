package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.commands.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.appointment.commands.dtos.CreateAppointmentRequest;
import inno.edu.api.domain.appointment.commands.mappers.CalculateAppointmentFeeRequestMapper;
import inno.edu.api.domain.appointment.commands.mappers.CreateAppointmentRequestMapper;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.profile.assertions.MenteeProfileExistsAssertion;
import inno.edu.api.domain.profile.assertions.MentorProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import java.math.BigDecimal;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static java.util.UUID.randomUUID;

@Command
public class CreateAppointmentCommand {
    private final CreateAppointmentRequestMapper createAppointmentRequestMapper;
    private final CalculateAppointmentFeeRequestMapper calculateAppointmentFeeRequestMapper;

    private final AppointmentRepository appointmentRepository;

    private final MenteeProfileExistsAssertion menteeProfileExistsAssertion;
    private final MentorProfileExistsAssertion mentorProfileExistsAssertion;

    private final CalculateAppointmentFeeCommand calculateAppointmentFeeCommand;

    public CreateAppointmentCommand(CreateAppointmentRequestMapper createAppointmentRequestMapper, CalculateAppointmentFeeRequestMapper calculateAppointmentFeeRequestMapper, AppointmentRepository appointmentRepository, MenteeProfileExistsAssertion menteeProfileExistsAssertion, MentorProfileExistsAssertion mentorProfileExistsAssertion, CalculateAppointmentFeeCommand calculateAppointmentFeeCommand) {
        this.createAppointmentRequestMapper = createAppointmentRequestMapper;
        this.calculateAppointmentFeeRequestMapper = calculateAppointmentFeeRequestMapper;
        this.appointmentRepository = appointmentRepository;
        this.menteeProfileExistsAssertion = menteeProfileExistsAssertion;
        this.mentorProfileExistsAssertion = mentorProfileExistsAssertion;
        this.calculateAppointmentFeeCommand = calculateAppointmentFeeCommand;
    }

    public Appointment run(CreateAppointmentRequest createAppointmentRequest) {
        menteeProfileExistsAssertion.run(createAppointmentRequest.getMenteeProfileId());
        mentorProfileExistsAssertion.run(createAppointmentRequest.getMentorProfileId());

        Appointment appointment = createAppointmentRequestMapper.toAppointment(createAppointmentRequest);

        appointment.setId(randomUUID());
        appointment.setStatus(PROPOSED);
        appointment.setFee(getAppointmentFee(appointment));

        return appointmentRepository.save(appointment);
    }

    private BigDecimal getAppointmentFee(Appointment appointment) {
        CalculateAppointmentFeeRequest calculateAppointmentFeeRequest =
                calculateAppointmentFeeRequestMapper.toAppointmentFeeRequest(appointment);

       return calculateAppointmentFeeCommand.run(calculateAppointmentFeeRequest);
    }
}
