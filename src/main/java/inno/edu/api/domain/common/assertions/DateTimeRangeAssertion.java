package inno.edu.api.domain.common.assertions;

import inno.edu.api.domain.common.exceptions.InvalidDateTimeRangeException;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.time.LocalDateTime;

@Assertion
public class DateTimeRangeAssertion {
    public void run(LocalDateTime from, LocalDateTime to) {
        if (from.isEqual(to) || from.isAfter(to)) {
            throw new InvalidDateTimeRangeException(from, to);
        }
    }
}