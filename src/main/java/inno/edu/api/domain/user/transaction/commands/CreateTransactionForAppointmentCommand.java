package inno.edu.api.domain.user.transaction.commands;

import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.profile.root.queries.GetProfileByIdQuery;
import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.models.TransactionType;
import inno.edu.api.domain.user.transaction.models.dtos.CreateTransactionRequest;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.COMPLETED;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.domain.user.transaction.models.TransactionType.CREDIT;
import static inno.edu.api.domain.user.transaction.models.TransactionType.DEBIT;

@Command
public class CreateTransactionForAppointmentCommand {
    private final GetAppointmentByIdQuery getAppointmentByIdQuery;
    private final CreateTransactionCommand createTransactionCommand;
    private final GetProfileByIdQuery getProfileByIdQuery;

    public CreateTransactionForAppointmentCommand(GetAppointmentByIdQuery getAppointmentByIdQuery, CreateTransactionCommand createTransactionCommand, GetProfileByIdQuery getProfileByIdQuery) {
        this.getAppointmentByIdQuery = getAppointmentByIdQuery;
        this.createTransactionCommand = createTransactionCommand;
        this.getProfileByIdQuery = getProfileByIdQuery;
    }

    public Transaction run(UUID appointmentId) {
        Appointment appointment = getAppointmentByIdQuery.run(appointmentId);

        TransactionType transactionType = appointment.getStatus() == PROPOSED
                ? DEBIT
                : CREDIT;

        UUID userId = appointment.getStatus() == COMPLETED
                ? getProfileByIdQuery.run(appointment.getMentorProfileId()).getUserId()
                : getProfileByIdQuery.run(appointment.getMenteeProfileId()).getUserId();

        CreateTransactionRequest transactionRequest = CreateTransactionRequest.builder()
                .appointmentId(appointment.getId())
                .value(appointment.getFee())
                .type(transactionType)
                .build();

        return createTransactionCommand.run(userId, transactionRequest);
    }
}
