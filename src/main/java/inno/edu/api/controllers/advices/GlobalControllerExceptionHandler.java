package inno.edu.api.controllers.advices;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.hateoas.VndErrors;
import org.springframework.hateoas.VndErrors.VndError;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.DateTimeException;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.substringBetween;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalControllerExceptionHandler {
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors exceptionHandler(RuntimeException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        List<VndError> errors =
                ex.getBindingResult().getFieldErrors().stream().map(fieldError -> new VndError("error",
                        format("Invalid value '%s' supplied for field '%s', %s.",
                                fieldError.getRejectedValue(),
                                fieldError.getField(),
                                fieldError.getDefaultMessage()))).collect(toList());
        return new VndErrors(errors);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors invalidFormatExceptionHandler(HttpMessageNotReadableException ex) {
        String errors = ex.getMessage();

        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException rootException = (InvalidFormatException) ex.getCause();

            String typeMismatch = "unknown reason";
            if (ex.getRootCause() instanceof DateTimeException) {
                typeMismatch = "invalid ISO datetime format";
            }

            errors = format("Invalid value '%s' supplied for field '%s', %s.",
                    rootException.getValue(),
                    substringBetween(rootException.getPath().toString(), "\"", "\""),
                    typeMismatch);
        }

        return new VndErrors("errors", errors);
    }
}

