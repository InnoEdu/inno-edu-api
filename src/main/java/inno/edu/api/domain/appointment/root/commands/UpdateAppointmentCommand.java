package inno.edu.api.domain.appointment.root.commands;

import inno.edu.api.domain.appointment.root.models.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.appointment.root.models.dtos.UpdateAppointmentRequest;
import inno.edu.api.domain.appointment.root.models.dtos.mappers.CalculateAppointmentFeeRequestMapper;
import inno.edu.api.domain.appointment.root.models.dtos.mappers.UpdateAppointmentRequestMapper;
import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.math.BigDecimal;
import java.util.UUID;

@Command
public class UpdateAppointmentCommand {
    private final UpdateAppointmentRequestMapper updateAppointmentRequestMapper;
    private final CalculateAppointmentFeeRequestMapper calculateAppointmentFeeRequestMapper;

    private final GetAppointmentByIdQuery getAppointmentByIdQuery;
    private final AppointmentRepository appointmentRepository;

    private final CalculateAppointmentFeeCommand calculateAppointmentFeeCommand;

    public UpdateAppointmentCommand(UpdateAppointmentRequestMapper updateAppointmentRequestMapper, CalculateAppointmentFeeRequestMapper calculateAppointmentFeeRequestMapper, GetAppointmentByIdQuery getAppointmentByIdQuery, AppointmentRepository appointmentRepository, CalculateAppointmentFeeCommand calculateAppointmentFeeCommand) {
        this.updateAppointmentRequestMapper = updateAppointmentRequestMapper;
        this.calculateAppointmentFeeRequestMapper = calculateAppointmentFeeRequestMapper;
        this.getAppointmentByIdQuery = getAppointmentByIdQuery;
        this.appointmentRepository = appointmentRepository;
        this.calculateAppointmentFeeCommand = calculateAppointmentFeeCommand;
    }

    public Appointment run(UUID id, UpdateAppointmentRequest updateAppointmentRequest) {
        Appointment currentAppointment = getAppointmentByIdQuery.run(id);
        updateAppointmentRequestMapper.setAppointment(updateAppointmentRequest, currentAppointment);

        currentAppointment.setFee(getAppointmentFee(currentAppointment));

        return appointmentRepository.save(currentAppointment);
    }

    private BigDecimal getAppointmentFee(Appointment appointment) {
        CalculateAppointmentFeeRequest calculateAppointmentFeeRequest =
                calculateAppointmentFeeRequestMapper.toAppointmentFeeRequest(appointment);

        return calculateAppointmentFeeCommand.run(calculateAppointmentFeeRequest);
    }
}
