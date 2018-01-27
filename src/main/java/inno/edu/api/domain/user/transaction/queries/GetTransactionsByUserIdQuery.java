package inno.edu.api.domain.user.transaction.queries;

import inno.edu.api.domain.user.root.assertions.UserExistsAssertion;
import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.repositories.TransactionRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

@Query
public class GetTransactionsByUserIdQuery {
    private final UserExistsAssertion userExistsAssertion;
    private final TransactionRepository transactionRepository;

    public GetTransactionsByUserIdQuery(UserExistsAssertion userExistsAssertion, TransactionRepository transactionRepository) {
        this.userExistsAssertion = userExistsAssertion;
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> run(UUID userId) {
        userExistsAssertion.run(userId);

        return transactionRepository.findByUserId(userId);
    }
}
