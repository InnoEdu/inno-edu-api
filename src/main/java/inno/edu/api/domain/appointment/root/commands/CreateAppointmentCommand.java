package inno.edu.api.domain.appointment.root.commands;

import inno.edu.api.domain.appointment.root.assertions.MenteeHasFundsForAppointmentAssertion;
import inno.edu.api.domain.appointment.root.models.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.appointment.root.models.dtos.CreateAppointmentRequest;
import inno.edu.api.domain.appointment.root.models.dtos.mappers.CalculateAppointmentFeeRequestMapper;
import inno.edu.api.domain.appointment.root.models.dtos.mappers.CreateAppointmentRequestMapper;
import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.user.transaction.commands.CreateTransactionForAppointmentCommand;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import java.math.BigDecimal;

import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.PROPOSED;

@Command
public class CreateAppointmentCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateAppointmentRequestMapper createAppointmentRequestMapper;
    private final CalculateAppointmentFeeRequestMapper calculateAppointmentFeeRequestMapper;

    private final AppointmentRepository appointmentRepository;

    private final ProfileExistsAssertion profileExistsAssertion;
    private final MenteeHasFundsForAppointmentAssertion menteeHasFundsForAppointmentAssertion;

    private final CalculateAppointmentFeeCommand calculateAppointmentFeeCommand;
    private final CreateTransactionForAppointmentCommand createTransactionForAppointmentCommand;

    public CreateAppointmentCommand(UUIDGeneratorService uuidGeneratorService, CreateAppointmentRequestMapper createAppointmentRequestMapper, CalculateAppointmentFeeRequestMapper calculateAppointmentFeeRequestMapper, AppointmentRepository appointmentRepository, ProfileExistsAssertion profileExistsAssertion, MenteeHasFundsForAppointmentAssertion menteeHasFundsForAppointmentAssertion, CalculateAppointmentFeeCommand calculateAppointmentFeeCommand, CreateTransactionForAppointmentCommand createTransactionForAppointmentCommand) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createAppointmentRequestMapper = createAppointmentRequestMapper;
        this.calculateAppointmentFeeRequestMapper = calculateAppointmentFeeRequestMapper;
        this.appointmentRepository = appointmentRepository;
        this.profileExistsAssertion = profileExistsAssertion;
        this.menteeHasFundsForAppointmentAssertion = menteeHasFundsForAppointmentAssertion;
        this.calculateAppointmentFeeCommand = calculateAppointmentFeeCommand;
        this.createTransactionForAppointmentCommand = createTransactionForAppointmentCommand;
    }

    public Appointment run(CreateAppointmentRequest createAppointmentRequest) {
        profileExistsAssertion.run(createAppointmentRequest.getMenteeProfileId());
        profileExistsAssertion.run(createAppointmentRequest.getMentorProfileId());

        Appointment appointment = createAppointmentRequestMapper.toAppointment(createAppointmentRequest);

        appointment.setId(uuidGeneratorService.generate());
        appointment.setStatus(PROPOSED);
        appointment.setFee(getAppointmentFee(appointment));

        menteeHasFundsForAppointmentAssertion.run(appointment);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        createTransactionForAppointmentCommand.run(savedAppointment.getId());
        return savedAppointment;
    }

    private BigDecimal getAppointmentFee(Appointment appointment) {
        CalculateAppointmentFeeRequest calculateAppointmentFeeRequest =
                calculateAppointmentFeeRequestMapper.toAppointmentFeeRequest(appointment);

       return calculateAppointmentFeeCommand.run(calculateAppointmentFeeRequest);
    }
}
