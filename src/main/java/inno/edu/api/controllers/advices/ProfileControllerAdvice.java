package inno.edu.api.controllers.advices;

import inno.edu.api.domain.profile.association.exceptions.ProfileAssociationNotFoundException;
import inno.edu.api.domain.profile.association.exceptions.PendingAssociationExistsException;
import inno.edu.api.domain.profile.root.exceptions.ProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.root.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.school.exceptions.SchoolNotFoundException;
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
public class ProfileControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors appointmentNotFoundExceptionHandler(ProfileNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ProfileAssociationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors associationNotFoundExceptionHandler(ProfileAssociationNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ProfileAlreadyCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors profileAlreadyCreatedExceptionHandler(ProfileAlreadyCreatedException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(PendingAssociationExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors pendingAssociationExistsExceptionHandler(PendingAssociationExistsException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(SchoolNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors schoolNotFoundExceptionHandler(SchoolNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

}
