package inno.edu.api.domain.user.transaction.queries;

import inno.edu.api.domain.user.root.assertions.UserExistsAssertion;
import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.repositories.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiTransactions;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetTransactionsByUserIdQueryTest {
    @Mock
    private UserExistsAssertion userExistsAssertion;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private GetTransactionsByUserIdQuery getTransactionsByUserIdQuery;

    @Test
    public void shouldReturnUserTransactions() {
        when(transactionRepository.findByUserId(fei().getId())).thenReturn(feiTransactions());

        List<Transaction> transactions = getTransactionsByUserIdQuery.run(fei().getId());

        assertThat(transactions, is(feiTransactions()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getTransactionsByUserIdQuery.run(fei().getId());

        verify(userExistsAssertion).run(fei().getId());
    }
}