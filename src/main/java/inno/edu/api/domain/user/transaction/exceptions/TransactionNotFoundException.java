package inno.edu.api.domain.user.transaction.exceptions;

import java.util.UUID;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(UUID transactionId) {
        super("Could not find transaction '" + transactionId.toString() + "'.");
    }
}