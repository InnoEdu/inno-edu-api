package inno.edu.api.domain.user.transaction.commands;

import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.models.dtos.mappers.UpdateTransactionRequestMapper;
import inno.edu.api.domain.user.transaction.queries.GetTransactionByIdQuery;
import inno.edu.api.domain.user.transaction.repositories.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.feiTransaction;
import static inno.edu.api.support.UserFactory.updateFeiTransactionRequest;
import static inno.edu.api.support.UserFactory.updatedFeiTransaction;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateTransactionCommandTest {
    @Mock
    private GetTransactionByIdQuery getTransactionByIdQuery;

    @Mock
    private  TransactionRepository transactionRepository;

    @Mock
    private  UpdateTransactionRequestMapper updateTransactionRequestMapper;

    @InjectMocks
    private UpdateTransactionCommand updateTransactionCommand;

    @Test
    public void shouldReturnUpdatedTransaction() {
        when(getTransactionByIdQuery.run(feiTransaction().getId())).thenReturn(feiTransaction());
        when(transactionRepository.save(feiTransaction())).thenReturn(updatedFeiTransaction());

        Transaction transaction = updateTransactionCommand.run(feiTransaction().getId(), updateFeiTransactionRequest());

        verify(updateTransactionRequestMapper).setTransaction(updateFeiTransactionRequest(), feiTransaction());

        assertThat(transaction, is(updatedFeiTransaction()));
    }
}