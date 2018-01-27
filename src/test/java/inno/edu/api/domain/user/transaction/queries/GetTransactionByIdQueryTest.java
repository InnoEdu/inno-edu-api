package inno.edu.api.domain.user.transaction.queries;

import inno.edu.api.domain.user.transaction.exceptions.TransactionNotFoundException;
import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.repositories.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.feiTransaction;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetTransactionByIdQueryTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private GetTransactionByIdQuery getTransactionByIdQuery;

    @Test(expected = TransactionNotFoundException.class)
    public void shouldThrowExceptionIfTransactionDoesNotExist() {
        when(transactionRepository.findOne(feiTransaction().getId())).thenReturn(null);

        getTransactionByIdQuery.run(feiTransaction().getId());
    }

    @Test
    public void shouldReturnTransaction() {
        when(transactionRepository.findOne(feiTransaction().getId())).thenReturn(feiTransaction());

        Transaction applicationTransaction = getTransactionByIdQuery.run(feiTransaction().getId());

        assertThat(applicationTransaction, is(feiTransaction()));
    }
}