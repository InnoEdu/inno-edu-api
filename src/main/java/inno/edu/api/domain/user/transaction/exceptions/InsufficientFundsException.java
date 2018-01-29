package inno.edu.api.domain.user.transaction.exceptions;

import java.math.BigDecimal;
import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(UUID userId, BigDecimal value) {
        super("Insufficient funds for user '" + userId.toString() + "' for an amount of '" + value + "'.");
    }
}
