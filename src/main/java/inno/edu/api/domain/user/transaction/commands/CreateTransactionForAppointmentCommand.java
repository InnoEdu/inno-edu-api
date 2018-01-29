package inno.edu.api.domain.user.transaction.commands;

import inno.edu.api.domain.appointment.root.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.root.models.Appointment;
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
    private final AppointmentExistsAssertion appointmentExistsAssertion;
    private final CreateTransactionCommand createTransactionCommand;

    public CreateTransactionForAppointmentCommand(AppointmentExistsAssertion appointmentExistsAssertion, CreateTransactionCommand createTransactionCommand) {
        this.appointmentExistsAssertion = appointmentExistsAssertion;
        this.createTransactionCommand = createTransactionCommand;
    }

    public Transaction run(Appointment appointment) {
        appointmentExistsAssertion.run(appointment.getId());

        TransactionType transactionType = appointment.getStatus() == PROPOSED
                ? DEBIT
                : CREDIT;

        UUID userId = appointment.getStatus() == COMPLETED
                ? appointment.getMentorProfile().getUserId()
                : appointment.getMenteeProfile().getUserId();

        CreateTransactionRequest transactionRequest = CreateTransactionRequest.builder()
                .appointmentId(appointment.getId())
                .value(appointment.getFee())
                .type(transactionType)
                .build();

        return createTransactionCommand.run(userId, transactionRequest);
    }
}
