package inno.edu.api.domain.user.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("Could not find user '" + userId + "'.");
    }
}