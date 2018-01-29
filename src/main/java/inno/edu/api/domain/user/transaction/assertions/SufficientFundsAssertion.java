package inno.edu.api.domain.user.transaction.assertions;

import inno.edu.api.domain.user.root.assertions.UserExistsAssertion;
import inno.edu.api.domain.user.transaction.exceptions.InsufficientFundsException;
import inno.edu.api.domain.user.transaction.queries.GetBalanceByUserIdQuery;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.math.BigDecimal;
import java.util.UUID;

@Assertion
public class SufficientFundsAssertion  {
    private final UserExistsAssertion userExistsAssertion;
    private final GetBalanceByUserIdQuery getBalanceByUserIdQuery;

    public SufficientFundsAssertion(UserExistsAssertion userExistsAssertion, GetBalanceByUserIdQuery getBalanceByUserIdQuery) {
        this.userExistsAssertion = userExistsAssertion;
        this.getBalanceByUserIdQuery = getBalanceByUserIdQuery;
    }

    public void run(UUID userId, BigDecimal value) {
        userExistsAssertion.run(userId);

        if (getBalanceByUserIdQuery.run(userId).getValue().compareTo(value) == -1) {
            throw new InsufficientFundsException(userId, value);
        }
    }
}
