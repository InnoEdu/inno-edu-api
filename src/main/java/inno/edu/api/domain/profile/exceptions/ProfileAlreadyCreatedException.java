package inno.edu.api.domain.profile.exceptions;

import java.util.UUID;

public class ProfileAlreadyCreatedException extends RuntimeException {
    public ProfileAlreadyCreatedException(UUID id) {
        super("A profile has already been created for user '" + id.toString() + "'.");
    }
}
