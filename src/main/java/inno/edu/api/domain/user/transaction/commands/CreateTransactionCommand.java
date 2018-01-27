package inno.edu.api.domain.user.transaction.commands;

import inno.edu.api.domain.appointment.root.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.user.root.assertions.UserExistsAssertion;
import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.models.dtos.CreateTransactionRequest;
import inno.edu.api.domain.user.transaction.models.dtos.mappers.CreateTransactionRequestMapper;
import inno.edu.api.domain.user.transaction.repositories.TransactionRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import java.util.UUID;

@Command
public class CreateTransactionCommand {
    private final UserExistsAssertion userExistsAssertion;
    private final AppointmentExistsAssertion appointmentExistsAssertion;

    private final UUIDGeneratorService uuidGeneratorService;
    private final TransactionRepository transactionRepository;
    private final CreateTransactionRequestMapper createTransactionRequestMapper;

    public CreateTransactionCommand(UserExistsAssertion userExistsAssertion, AppointmentExistsAssertion appointmentExistsAssertion, UUIDGeneratorService uuidGeneratorService, TransactionRepository transactionRepository, CreateTransactionRequestMapper createTransactionRequestMapper) {
        this.userExistsAssertion = userExistsAssertion;
        this.appointmentExistsAssertion = appointmentExistsAssertion;
        this.uuidGeneratorService = uuidGeneratorService;
        this.transactionRepository = transactionRepository;
        this.createTransactionRequestMapper = createTransactionRequestMapper;
    }

    public Transaction run(UUID userId, CreateTransactionRequest request) {
        runAssertions(userId, request);

        Transaction transaction = createTransactionRequestMapper.toTransaction(request);
        transaction.setId(uuidGeneratorService.generate());
        transaction.setUserId(userId);

        return transactionRepository.save(transaction);
    }

    private void runAssertions(UUID userId, CreateTransactionRequest request) {
        userExistsAssertion.run(userId);

        if (request.getAppointmentId() != null) {
            appointmentExistsAssertion.run(request.getAppointmentId());
        }
    }
}

