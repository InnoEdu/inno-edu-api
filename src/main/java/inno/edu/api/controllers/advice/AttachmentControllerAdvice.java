package inno.edu.api.controllers.advice;

import inno.edu.api.domain.attachment.exceptions.AttachmentNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.hateoas.VndErrors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static inno.edu.api.infrastructure.configuration.StaticConstants.ERROR;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class AttachmentControllerAdvice {
    @ResponseBody
    @ExceptionHandler(AttachmentNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    VndErrors attachmentNotFoundExceptionHandler(AttachmentNotFoundException ex) {
        return new VndErrors(ERROR, ex.getMessage());
    }
}
