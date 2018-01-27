package inno.edu.api.domain.user.transaction.commands;

import inno.edu.api.domain.user.transaction.assertions.TransactionExistsAssertion;
import inno.edu.api.domain.user.transaction.repositories.TransactionRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteTransactionCommand {
    private final TransactionExistsAssertion transactionExistsAssertion;
    private final TransactionRepository transactionRepository;

    public DeleteTransactionCommand(TransactionExistsAssertion transactionExistsAssertion, TransactionRepository transactionRepository) {
        this.transactionExistsAssertion = transactionExistsAssertion;
        this.transactionRepository = transactionRepository;
    }

    public void run(UUID transactionId) {
        transactionExistsAssertion.run(transactionId);
        transactionRepository.delete(transactionId);
    }
}
