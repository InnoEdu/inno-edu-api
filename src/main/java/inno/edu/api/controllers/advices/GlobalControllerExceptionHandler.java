package inno.edu.api.controllers.advices;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import inno.edu.api.infrastructure.storage.exceptions.S3StorageException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.hateoas.VndErrors;
import org.springframework.hateoas.VndErrors.VndError;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.DateTimeException;
import java.util.List;

import static inno.edu.api.infrastructure.configuration.StaticConstants.ERROR;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.substringBetween;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalControllerExceptionHandler {
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(BAD_REQUEST)
    VndErrors exceptionHandler(RuntimeException ex) {
        return new VndErrors(ERROR, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(S3StorageException.class)
    @ResponseStatus(BAD_REQUEST)
    VndErrors s3StorageExceptionHandler(S3StorageException ex) {
        return new VndErrors(ERROR, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(FORBIDDEN)
    VndErrors accessDeniedExceptionHandler(AccessDeniedException ex) {
        return new VndErrors(ERROR, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(FORBIDDEN)
    VndErrors expiredJwtExceptionHandler(ExpiredJwtException ex) {
        return new VndErrors(ERROR, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    VndErrors methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        List<VndError> errors =
                ex.getBindingResult().getFieldErrors().stream().map(fieldError -> new VndError(ERROR,
                        format("Invalid value '%s' supplied for field '%s', %s.",
                                fieldError.getRejectedValue(),
                                fieldError.getField(),
                                fieldError.getDefaultMessage()))).collect(toList());
        return new VndErrors(errors);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    VndErrors invalidFormatExceptionHandler(HttpMessageNotReadableException ex) {
        String errors = ex.getMessage();

        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException rootException = (InvalidFormatException) ex.getCause();

            String typeMismatch = "unknown reason: " + ex.getRootCause().toString();
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

