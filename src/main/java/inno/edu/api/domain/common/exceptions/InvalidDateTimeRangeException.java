package inno.edu.api.domain.common.exceptions;

import java.time.LocalDateTime;

public class InvalidDateTimeRangeException extends RuntimeException{
    public InvalidDateTimeRangeException(LocalDateTime from, LocalDateTime to) {
        super("From '" + from.toString() + "' to '" + to.toString() + "' is an invalid date time range.");
    }
}
