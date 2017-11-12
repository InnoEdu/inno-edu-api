package inno.edu.api.domain.user.exceptions;

public class UserNameAlreadyExistsException extends RuntimeException {
    public UserNameAlreadyExistsException(String userName) {
        super("User name '" + userName + "' already exists.");
    }
}
