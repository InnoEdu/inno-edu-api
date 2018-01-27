package inno.edu.api.domain.user.transaction.commands;

import inno.edu.api.domain.user.transaction.assertions.TransactionExistsAssertion;
import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.models.dtos.UpdateTransactionRequest;
import inno.edu.api.domain.user.transaction.models.dtos.mappers.UpdateTransactionRequestMapper;
import inno.edu.api.domain.user.transaction.queries.GetTransactionByIdQuery;
import inno.edu.api.domain.user.transaction.repositories.TransactionRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateTransactionCommand {
    private final GetTransactionByIdQuery getTransactionByIdQuery;

    private final TransactionRepository transactionRepository;
    private final UpdateTransactionRequestMapper updateTransactionRequestMapper;

    public UpdateTransactionCommand(TransactionExistsAssertion transactionExistsAssertion, GetTransactionByIdQuery getTransactionByIdQuery, TransactionRepository transactionRepository, UpdateTransactionRequestMapper updateTransactionRequestMapper) {
        this.getTransactionByIdQuery = getTransactionByIdQuery;
        this.transactionRepository = transactionRepository;
        this.updateTransactionRequestMapper = updateTransactionRequestMapper;
    }

    public Transaction run(UUID transactionId, UpdateTransactionRequest request) {
        Transaction transaction = getTransactionByIdQuery.run(transactionId);
        updateTransactionRequestMapper.setTransaction(request, transaction);

        return transactionRepository.save(transaction);
    }
}

