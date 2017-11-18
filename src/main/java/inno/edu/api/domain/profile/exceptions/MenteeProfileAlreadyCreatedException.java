package inno.edu.api.domain.profile.exceptions;

import java.util.UUID;

public class MenteeProfileAlreadyCreatedException extends RuntimeException {
    public MenteeProfileAlreadyCreatedException(UUID menteeId) {
        super("A profile has already been created for mentee '" + menteeId.toString() + "'.");
    }
}
