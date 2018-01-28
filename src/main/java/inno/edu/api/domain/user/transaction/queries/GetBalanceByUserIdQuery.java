package inno.edu.api.domain.user.transaction.queries;

import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import inno.edu.api.domain.user.transaction.models.Balance;
import inno.edu.api.infrastructure.annotations.Query;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.UUID;

import static inno.edu.api.domain.user.transaction.models.QTransaction.transaction;
import static inno.edu.api.domain.user.transaction.models.TransactionType.DEBIT;
import static java.math.BigDecimal.ONE;

@Query
public class GetBalanceByUserIdQuery {
    private final EntityManager entityManager;

    public GetBalanceByUserIdQuery(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Balance run(UUID userId) {
        return Balance.builder()
                .userId(userId)
                .value(getTransactionSum(userId))
                .build();
    }

    BigDecimal getTransactionSum(UUID userId) {
        JPAQuery<Balance> query = new JPAQuery<>(entityManager);

        NumberExpression<BigDecimal> balance = transaction
                .type
                .when(DEBIT).then(new BigDecimal(-1))
                .otherwise(ONE)
                .multiply(transaction.value)
                .sum();

        return query
                .from(transaction)
                .select(balance)
                .where(transaction.userId.eq(userId))
                .fetchOne();
    }
}
