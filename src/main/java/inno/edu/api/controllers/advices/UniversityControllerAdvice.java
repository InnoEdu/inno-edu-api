package inno.edu.api.controllers.advices;

import inno.edu.api.domain.university.exceptions.UniversityNotFoundException;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UniversityControllerAdvice {

    @ResponseBody
    @ExceptionHandler(UniversityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors universityNotFoundExceptionHandler(UserNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

}
