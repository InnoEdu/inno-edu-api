package inno.edu.api.domain.common.assertions;

import inno.edu.api.domain.common.exceptions.InvalidDateTimeRangeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static java.time.LocalDateTime.now;

@RunWith(MockitoJUnitRunner.class)
public class DateTimeRangeAssertionTest {
    @InjectMocks
    private DateTimeRangeAssertion dateTimeRangeAssertion;

    @Test
    public void shouldNotRaiseExceptionForValidDates() {
        dateTimeRangeAssertion.run(now(), now().plusDays(1));
    }

    @Test(expected = InvalidDateTimeRangeException.class)
    public void shouldRaiseExceptionForInvalidDates() {
        dateTimeRangeAssertion.run(now(), now().minusDays(1));
    }
}