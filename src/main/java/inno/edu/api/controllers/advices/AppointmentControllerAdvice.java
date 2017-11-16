package inno.edu.api.controllers.advices;

import inno.edu.api.domain.appointment.exceptions.AppointmentNotFoundException;
import org.springframework.hateoas.VndErrors.VndError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AppointmentControllerAdvice {
    @ResponseBody
    @ExceptionHandler(AppointmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndError appointmentNotFoundExceptionHandler(AppointmentNotFoundException ex) {
        return new VndError("error", ex.getMessage());
    }
}
