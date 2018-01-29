package inno.edu.api.domain.user.transaction.assertions;

import inno.edu.api.domain.user.root.assertions.UserExistsAssertion;
import inno.edu.api.domain.user.transaction.exceptions.InsufficientFundsException;
import inno.edu.api.domain.user.transaction.queries.GetBalanceByUserIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiBalance;
import static java.math.BigDecimal.ONE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SufficientFundsAssertionTest {
    @Mock
    private UserExistsAssertion userExistsAssertion;

    @Mock
    private GetBalanceByUserIdQuery getBalanceByUserIdQuery;

    @InjectMocks
    private SufficientFundsAssertion sufficientFundsAssertion;

    @Before
    public void setUp() {
        when(getBalanceByUserIdQuery.run(fei().getId())).thenReturn(feiBalance());
    }
    @Test
    public void shouldNotThrowExceptionIsBalanceIsEqualToValue() {
        sufficientFundsAssertion.run(fei().getId(), feiBalance().getValue());
    }

    @Test
    public void shouldNotThrowExceptionIsBalanceIsLargerThanValue() {
        sufficientFundsAssertion.run(fei().getId(), feiBalance().getValue().min(ONE));
    }

    @Test(expected = InsufficientFundsException.class)
    public void shouldThrowExceptionIsBalanceIsLessThanValue() {
        sufficientFundsAssertion.run(fei().getId(), feiBalance().getValue().add(ONE));
    }

    @Test
    public void shouldRunAllAssertions() {
        sufficientFundsAssertion.run(fei().getId(), feiBalance().getValue());

        verify(userExistsAssertion).run(fei().getId());
    }
}