package inno.edu.api.domain.user.transaction.commands;

import inno.edu.api.domain.user.transaction.assertions.TransactionExistsAssertion;
import inno.edu.api.domain.user.transaction.repositories.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.feiTransaction;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteTransactionCommandTest {
    @Mock
    private TransactionExistsAssertion transactionExistsAssertion;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private DeleteTransactionCommand deleteTransactionCommand;

    @Test
    public void shouldCallRepositoryToDeleteTransaction() {
        deleteTransactionCommand.run(feiTransaction().getId());

        verify(transactionRepository).delete(feiTransaction().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteTransactionCommand.run(feiTransaction().getId());

        verify(transactionExistsAssertion).run(feiTransaction().getId());
    }

}