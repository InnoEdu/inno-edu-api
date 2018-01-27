package inno.edu.api.domain.user.transaction.queries;

import inno.edu.api.domain.user.transaction.exceptions.TransactionNotFoundException;
import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.repositories.TransactionRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetTransactionByIdQuery {
    private final TransactionRepository transactionRepository;

    public GetTransactionByIdQuery(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction run(UUID id) {
        return ofNullable(transactionRepository.findOne(id))
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }
}
