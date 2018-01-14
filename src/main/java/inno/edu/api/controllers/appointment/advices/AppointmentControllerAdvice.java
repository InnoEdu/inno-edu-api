package inno.edu.api.controllers.appointment.advices;

import inno.edu.api.domain.appointment.root.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.feedback.exceptions.InvalidRatingRangeException;
import inno.edu.api.domain.profile.root.exceptions.ProfileNotFoundException;
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
public class AppointmentControllerAdvice {
    @ResponseBody
    @ExceptionHandler(AppointmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors appointmentNotFoundExceptionHandler(AppointmentNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidRatingRangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors invalidRatingRangeExceptionHandler(InvalidRatingRangeException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors profileNotFoundExceptionHandler(ProfileNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
