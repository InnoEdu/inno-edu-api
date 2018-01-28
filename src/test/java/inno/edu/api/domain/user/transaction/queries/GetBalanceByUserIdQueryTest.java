package inno.edu.api.domain.user.transaction.queries;

import inno.edu.api.domain.user.root.assertions.UserExistsAssertion;
import inno.edu.api.domain.user.transaction.models.Balance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;

import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiBalance;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GetBalanceByUserIdQueryTest {
    @Mock
    private UserExistsAssertion userExistsAssertion;

    @Mock
    private EntityManager entityManager;

    @Spy
    @InjectMocks
    private GetBalanceByUserIdQuery getBalanceByUserIdQuery;

    @Before
    public void setUp() {
        doReturn(feiBalance().getValue())
                .when(getBalanceByUserIdQuery)
                .getTransactionSum(fei().getId());

    }

    @Test
    public void shouldReturnUserBalance() {
        Balance balance = getBalanceByUserIdQuery.run(fei().getId());

        assertThat(balance, is(feiBalance()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getBalanceByUserIdQuery.run(fei().getId());

        verify(userExistsAssertion).run(fei().getId());
    }
}