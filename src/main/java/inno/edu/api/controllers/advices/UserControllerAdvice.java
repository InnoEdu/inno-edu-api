package inno.edu.api.controllers.advices;

import inno.edu.api.domain.user.exceptions.InvalidUserNameOrPasswordException;
import inno.edu.api.domain.user.exceptions.UserNameAlreadyExistsException;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.exceptions.UserProfileNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserControllerAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors userNotFoundExceptionHandler(UserNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserNameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors userNameAlreadyExistsExceptionHandler(UserNameAlreadyExistsException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors userProfileNotFoundExceptionHandler(UserProfileNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidUserNameOrPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    VndErrors invalidUserNameOrPasswordExceptionHandler(InvalidUserNameOrPasswordException ex) {
        return new VndErrors("error", ex.getMessage());
    }

}
