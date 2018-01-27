package inno.edu.api.controllers.user;

import inno.edu.api.domain.user.transaction.commands.CreateTransactionCommand;
import inno.edu.api.domain.user.transaction.commands.DeleteTransactionCommand;
import inno.edu.api.domain.user.transaction.commands.UpdateTransactionCommand;
import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.models.resources.TransactionResource;
import inno.edu.api.domain.user.transaction.queries.GetTransactionByIdQuery;
import inno.edu.api.domain.user.transaction.queries.GetTransactionsByUserIdQuery;
import inno.edu.api.infrastructure.web.ResourceBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.UserFactory.createFeiTransactionRequest;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiTransaction;
import static inno.edu.api.support.UserFactory.feiTransactions;
import static inno.edu.api.support.UserFactory.updateFeiTransactionRequest;
import static inno.edu.api.support.UserFactory.updatedFeiTransaction;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private GetTransactionByIdQuery getTransactionByIdQuery;

    @Mock
    private GetTransactionsByUserIdQuery getTransactionsByUserIdQuery;

    @Mock
    private CreateTransactionCommand createTransactionCommand;

    @Mock
    private UpdateTransactionCommand updateTransactionCommand;

    @Mock
    private DeleteTransactionCommand deleteTransactionCommand;

    @InjectMocks
    private TransactionController transactionController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldListTransactionsForUser() {
        when(getTransactionsByUserIdQuery.run(fei().getId())).thenReturn(feiTransactions());

        transactionController.all(fei().getId());

        verify(resourceBuilder).wrappedFrom(eq(feiTransactions()), any(), eq(TransactionResource.class));
    }

    @Test
    public void shouldGetTransactionById() {
        when(getTransactionByIdQuery.run(eq(feiTransaction().getId()))).thenReturn(feiTransaction());

        TransactionResource transactionResource = transactionController.get(feiTransaction().getId());

        assertThat(transactionResource.getTransaction(), is(feiTransaction()));
    }

    @Test
    public void shouldCreateNewTransaction() {
        when(createTransactionCommand.run(fei().getId(), createFeiTransactionRequest())).thenReturn(feiTransaction());

        ResponseEntity<Transaction> entity = transactionController.post(fei().getId(), createFeiTransactionRequest());

        assertThat(entity.getBody(), is(feiTransaction()));
    }

    @Test
    public void shouldUpdateTransaction() {
        when(updateTransactionCommand.run(feiTransaction().getId(), updateFeiTransactionRequest())).thenReturn(updatedFeiTransaction());

        ResponseEntity<Transaction> entity = transactionController.put(feiTransaction().getId(), updateFeiTransactionRequest());

        assertThat(entity.getBody(), is(updatedFeiTransaction()));
    }

    @Test
    public void shouldDeleteTransaction() {
        transactionController.delete(feiTransaction().getId());

        verify(deleteTransactionCommand).run(feiTransaction().getId());
    }
}