package inno.edu.api.controllers.advices;

import inno.edu.api.domain.profile.exceptions.MenteeProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
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
public class MenteeProfileControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors appointmentNotFoundExceptionHandler(ProfileNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MenteeProfileAlreadyCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors menteeProfileAlreadyCreatedExceptionHandler(MenteeProfileAlreadyCreatedException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
