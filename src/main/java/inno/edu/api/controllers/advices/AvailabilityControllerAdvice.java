package inno.edu.api.controllers.advices;

import inno.edu.api.domain.availability.exceptions.AvailabilityNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class AvailabilityControllerAdvice {

    @ResponseBody
    @ExceptionHandler(AvailabilityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors availabilityNotFoundExceptionHandler(AvailabilityNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

}
