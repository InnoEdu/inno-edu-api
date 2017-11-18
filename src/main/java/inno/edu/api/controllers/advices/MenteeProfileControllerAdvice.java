package inno.edu.api.controllers.advices;

import inno.edu.api.domain.profile.exceptions.MenteeProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import org.springframework.hateoas.VndErrors.VndError;
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
    VndError appointmentNotFoundExceptionHandler(ProfileNotFoundException ex) {
        return new VndError("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MenteeProfileAlreadyCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndError menteeProfileAlreadyCreatedExceptionHandler(MenteeProfileAlreadyCreatedException ex) {
        return new VndError("error", ex.getMessage());
    }
}
