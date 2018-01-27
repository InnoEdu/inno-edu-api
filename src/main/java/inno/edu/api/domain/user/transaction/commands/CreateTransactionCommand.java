package inno.edu.api.domain.user.transaction.commands;

import inno.edu.api.domain.appointment.root.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.user.root.assertions.UserExistsAssertion;
import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.models.dtos.CreateTransactionRequest;
import inno.edu.api.domain.user.transaction.models.dtos.mappers.CreateTransactionRequestMapper;
import inno.edu.api.domain.user.transaction.repositories.TransactionRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

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

    public Transaction run(CreateTransactionRequest request) {
        runAssertions(request);

        Transaction transaction = createTransactionRequestMapper.toTransaction(request);
        transaction.setId(uuidGeneratorService.generate());

        return transactionRepository.save(transaction);
    }

    private void runAssertions(CreateTransactionRequest request) {
        userExistsAssertion.run(request.getUserId());

        if (request.getAppointmentId() != null) {
            appointmentExistsAssertion.run(request.getAppointmentId());
        }
    }
}

