package inno.edu.api.controllers.advices;

import inno.edu.api.domain.profile.root.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.user.root.exceptions.InvalidUsernameOrPasswordException;
import inno.edu.api.domain.user.root.exceptions.UserIsNotMentorException;
import inno.edu.api.domain.user.root.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.root.exceptions.UsernameAlreadyExistsException;
import org.springframework.core.annotation.Order;
import org.springframework.hateoas.VndErrors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static inno.edu.api.infrastructure.configuration.StaticConstants.ERROR;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class UserControllerAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    VndErrors userNotFoundExceptionHandler(UserNotFoundException ex) {
        return new VndErrors(ERROR, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    VndErrors usernameAlreadyExistsExceptionHandler(UsernameAlreadyExistsException ex) {
        return new VndErrors(ERROR, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserProfileNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    VndErrors userProfileNotFoundExceptionHandler(UserProfileNotFoundException ex) {
        return new VndErrors(ERROR, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    @ResponseStatus(UNAUTHORIZED)
    VndErrors invalidUsernameOrPasswordExceptionHandler(InvalidUsernameOrPasswordException ex) {
        return new VndErrors(ERROR, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserIsNotMentorException.class)
    @ResponseStatus(BAD_REQUEST)
    VndErrors userIsNotMentorExceptionHandler(UserIsNotMentorException ex) {
        return new VndErrors(ERROR, ex.getMessage());
    }
}
