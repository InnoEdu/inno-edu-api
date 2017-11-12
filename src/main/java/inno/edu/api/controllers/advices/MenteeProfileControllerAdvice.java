package inno.edu.api.controllers.advices;

import inno.edu.api.domain.user.exceptions.MenteeProfileAlreadyCreatedException;
import inno.edu.api.domain.user.exceptions.ProfileNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
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
