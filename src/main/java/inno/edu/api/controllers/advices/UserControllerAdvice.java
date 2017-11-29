package inno.edu.api.controllers.advices;

import inno.edu.api.domain.profile.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.user.exceptions.InvalidUsernameOrPasswordException;
import inno.edu.api.domain.user.exceptions.UserIsNotMentorException;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.exceptions.UsernameAlreadyExistsException;
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
public class UserControllerAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors userNotFoundExceptionHandler(UserNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors usernameAlreadyExistsExceptionHandler(UsernameAlreadyExistsException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors userProfileNotFoundExceptionHandler(UserProfileNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    VndErrors invalidUserNameOrPasswordExceptionHandler(InvalidUsernameOrPasswordException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserIsNotMentorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors userIsNotMentorExceptionHandler(UserIsNotMentorException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
