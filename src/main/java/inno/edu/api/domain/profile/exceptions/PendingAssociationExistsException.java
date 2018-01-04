package inno.edu.api.domain.profile.exceptions;

import java.util.UUID;

public class PendingAssociationExistsException extends RuntimeException {
    public PendingAssociationExistsException(UUID id) {
        super("A profile already has an association request '" + id.toString() + "'.");
    }
}
