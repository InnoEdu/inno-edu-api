package inno.edu.api.domain.user.transaction.commands;

import inno.edu.api.domain.appointment.root.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.user.root.assertions.UserExistsAssertion;
import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.models.dtos.mappers.CreateTransactionRequestMapper;
import inno.edu.api.domain.user.transaction.repositories.TransactionRepository;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.createFeiTransactionRequest;
import static inno.edu.api.support.UserFactory.feiTransaction;
import static inno.edu.api.support.UserFactory.newFeiTransaction;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateTransactionCommandTest {
    @Mock
    private UserExistsAssertion userExistsAssertion;

    @Mock
    private AppointmentExistsAssertion appointmentExistsAssertion;

    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CreateTransactionRequestMapper createTransactionRequestMapper;

    @InjectMocks
    private CreateTransactionCommand createTransactionCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());
        when(createTransactionRequestMapper.toTransaction(createFeiTransactionRequest())).thenReturn(newFeiTransaction(null));
    }

    @Test
    public void shouldCallRepositoryToSaveTransaction() {
        Transaction newTransaction = newFeiTransaction(uuidGeneratorService.generate());

        when(transactionRepository.save(newTransaction)).thenReturn(newTransaction);

        Transaction savedTransaction = createTransactionCommand.run(feiTransaction().getUserId(), createFeiTransactionRequest());
        assertThat(savedTransaction, is(newTransaction));
    }


    @Test
    public void shouldRunAssertions() {
        createTransactionCommand.run(feiTransaction().getUserId(), createFeiTransactionRequest());

        verify(userExistsAssertion).run(feiTransaction().getUserId());
        verify(appointmentExistsAssertion).run(createFeiTransactionRequest().getAppointmentId());
    }
}